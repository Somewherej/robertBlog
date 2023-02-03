package com.robertBlog.exception;

import com.robertBlog.enums.AppHttpCodeEnum;

/**
 * @author Somewherej
 * @date 2022-11-21 20:21
 * @description  开发过程中需要进行很多的判断校验 如果出现了非法情况
 *               实际上我们是期望相应对应的提示的
 *               如果每次都自己手动去处理就很麻烦
 *               所以我们可以选择直接抛出异常  对异常进行统一的处理
 *               然后将一场中的信息封装成ResponseResult给前端
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