package com.robertBlog.handler.security;

import com.alibaba.fastjson.JSON;
import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.enums.AppHttpCodeEnum;
import com.robertBlog.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Somewherej
 * @date 2022-11-22 15:13
 * @description AccessDeniedHandler授权失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //打印异常信息 方便调试
        accessDeniedException.printStackTrace();
        //因为我们响应给前端时都是用ResponseResult格式  无权限操作
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
        System.out.println("程序初始化");
    }
}
