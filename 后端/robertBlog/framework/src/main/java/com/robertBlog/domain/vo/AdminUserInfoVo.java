package com.robertBlog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Somewherej
 * @Date 2022-11-26 21:32
 * @Description
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    //权限信息   字符串集合
    private List<String> permissions;
    //角色信息
    private List<String> roles;
    private UserInfoVo user;
}
