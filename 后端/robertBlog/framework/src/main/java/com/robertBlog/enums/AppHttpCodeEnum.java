package com.robertBlog.enums;

/**
 * @author Somewherej
 * @data 2022-11-16 10:55
 * @description  http状态码枚举类 代码复用性高 直接修改这个枚举类就行了
 */

public enum AppHttpCodeEnum {
    /**
     * http状态码枚举所有状态码注解
     */
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    LOGIN_ERROR(505,"用户名或密码错误");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}