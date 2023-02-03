package com.robertBlog.handler.exception;

import com.robertBlog.domain.entity.domain.ResponseResult;
import com.robertBlog.enums.AppHttpCodeEnum;
import com.robertBlog.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Somewherej
 * @date 2022-11-22 15:29
 * @description   全局异常处理
 */
@RestControllerAdvice  //(@ControllerAdvice组合@RequestBody)全部异常处理返回json，那么可以使用 @RestControllerAdvice代替ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 函数说明:
     *   获取的异常对象是systemException
     */
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    /**
     * 函数说明:
     *   获取的异常对象是exceptio
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回、
        //getMessage()打印出来方便调试
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}