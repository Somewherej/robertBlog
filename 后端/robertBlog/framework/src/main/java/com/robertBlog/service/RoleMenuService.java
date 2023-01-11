package com.robertBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robertBlog.domain.entity.RoleMenu;

/**
 * @author Somewherej
 * @Date 2022-12-14 10:55
 * @Description
 */
public interface RoleMenuService extends IService<RoleMenu> {
    void deleteRoleMenuByRoleId(Long id);
}