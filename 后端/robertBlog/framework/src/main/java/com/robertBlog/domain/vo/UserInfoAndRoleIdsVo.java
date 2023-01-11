package com.robertBlog.domain.vo;

import com.robertBlog.domain.entity.Role;
import com.robertBlog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Somewherej
 * @Date 2022-12-12 11:20
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {
    private User user;
    private List<Role> roles;
    private List<Long> roleIds;
}