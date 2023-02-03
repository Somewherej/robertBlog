package com.robertBlog.handler.security;

import com.alibaba.fastjson.JSON;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.enums.AppHttpCodeEnum;
import com.robertBlog.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Somewherej
 * @date 2022-11-22 15:11
 * @description
 *
 * 当我们的项目在认证出错or权限不足的时候响应回来的Json是Security的异常处理结果
 * 但是这个相应的格式是不符合要求的，所以需要自动定义异常处理
 * AccessDeniedHandler认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //打印异常信息 方便调试
        authException.printStackTrace();
        //认证失败有好几种可能 对异常类型进行判断
        //因为我们响应给前端时都是用ResponseResult格式
        ResponseResult result = null;  //初始化
        if(authException instanceof BadCredentialsException){
            // 用户名或者密码错误
            // 登录异常
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            // 没有客户端身份验证.尝试添加适当的身份验证筛选器
            // 需要登录
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
             //系统错误
             result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}