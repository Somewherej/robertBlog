package com.robertBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robertBlog.domain.entity.RoleMenu;
import com.robertBlog.mapper.RoleMenuMapper;
import com.robertBlog.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @author Somewherej
 * @Date 2022-12-12 17:43
 * @Description
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void deleteRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        remove(queryWrapper);
    }
}
