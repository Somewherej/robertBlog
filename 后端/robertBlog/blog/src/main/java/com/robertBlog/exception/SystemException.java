package com.robertBlog.exception;

import com.robertBlog.enums.AppHttpCodeEnum;

/**
 * @author Somewherej
 * @Date 2022-11-21 20:21
 * @Description
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}