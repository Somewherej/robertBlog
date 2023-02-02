package com.robertBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.robertBlog.domain.entity.LoginUser;
import com.robertBlog.domain.entity.User;
import com.robertBlog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Objects;
/**
 * @author Somewherej
 * @date 2023-01-11 2:09
 * @description  重写UserDetailService(让它去数据库进行查询)
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //使用userMapper进行数据库的查询
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        // selectOne单值查询
        User user = userMapper.selectOne(queryWrapper);
        // 判断是否查到用户  如果没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        /* *
        *  为啥不直接返回User?(报错)
        *  因为User跟UserDetails没关系
        *  重新定义LoginUser来实现UserDetails
        *  public class LoginUser implements UserDetails
        */
        return new LoginUser(user);

    }
}
