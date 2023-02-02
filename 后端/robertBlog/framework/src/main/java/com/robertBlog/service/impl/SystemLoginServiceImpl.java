package com.robertBlog.service.impl;

/**
 * @author Somewherej
 * @Date 2022-11-23 11:41
 * @Description
 */


import com.robertBlog.domain.entity.LoginUser;
import com.robertBlog.domain.entity.User;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.service.LoginService;
import com.robertBlog.utils.JwtUtil;
import com.robertBlog.utils.RedisCache;
import com.robertBlog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Somewherej
 * @date 2022-11-19 16:44
 * @description 后台LoginService的实现类SystemLoginServiceImpl
 */

@Service
public class SystemLoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /* *
     * 函数说明:
     *   后台管理系统登录
     */
    @Override
    public ResponseResult login(User user){
        //通过用户名密码进行认证
        //将用户输入封装
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //调用authenticationManager.authenticate()方法进行验证
        //authenticationManager会调用UserDetailService(默认的是在内存中进行查询)来进行验证,
        //因此需要重写UserDetailService(让它去数据库进行查询)
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){  //null
            throw new RuntimeException("用户名或密码错误");
        }
        //获得userid生成token
        //authenticate.getPrincipal()获取认证的主体  再强转
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        /* *
        * JWT: Json Web Token，是基于Json的一个公开规范，这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息，他的两大使用场景是：认证和数据交换
        * 使用起来就是，由服务端根据规范生成一个令牌（token），并且发放给客户端。此时客户端请求服务端的时候就可以携带者令牌，以令牌来证明自己的身份信息。
        * 类似session保持登录状态 的办法，通过token来代表用户身份。
        * */
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存在redis, 速度会比较快
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    /**
     * 函数说明:
     *   后台管理系统退出
     */
    @Override
    public ResponseResult logout() {
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}