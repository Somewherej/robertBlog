package com.robertBlog.service;

import com.robertBlog.domain.entity.User;
import com.robertBlog.domain.entity.domain.ResponseResult;
import org.springframework.context.annotation.Bean;

/**
 * @author Somewherej
 * @Date 2022-12-12 1:43
 * @Description
 */
public interface LoginService {
    ResponseResult login(User user);
    ResponseResult logout();
}
