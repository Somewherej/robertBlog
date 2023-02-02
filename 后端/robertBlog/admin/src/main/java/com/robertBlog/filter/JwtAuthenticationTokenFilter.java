package com.robertBlog.filter;

import com.alibaba.fastjson.JSON;
import com.robertBlog.domain.entity.LoginUser;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.enums.AppHttpCodeEnum;
import com.robertBlog.utils.JwtUtil;
import com.robertBlog.utils.RedisCache;
import com.robertBlog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Somewherej
 * @date 2022-12-31 5:44
 * @description  实现登录校验过滤器JwtAuthenticationTokenFilter
 *
 *    1.获取token
 *    2.从token中获取userid
 *    3.从redis中获取用户信息
 *    4.存入SecurityContextHolder(保存应用程序中当前使用人的安全上下文)
 *      说人话就是保存当前用户的用户信息
 */

//注入容器
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    /* *
     *  函数说明: 过滤器
     *  doFilterInternal是OncePerRequestFilter 中的一个抽象方法
     *  OncePerRequestFilter能够确保在一次请求中只通过一次filter，而不会重复执行
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("token");
        // 因为我们不是所有的请求都需要带token
        // 对于那些不需要token的接口 直接放行就好了
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法(因为我们在JWT工具类中已经有设置这个超时时间 or  发过来的token被篡改了)
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            //因为这边的异常是不能被controller处理的  所有我们需要自己来处理
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        //如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
