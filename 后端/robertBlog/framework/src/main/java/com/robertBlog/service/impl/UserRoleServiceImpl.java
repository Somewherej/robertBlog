package com.robertBlog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robertBlog.domain.entity.UserRole;
import com.robertBlog.mapper.UserRoleMapper;
import com.robertBlog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author Somewherej
 * @Date 2022-12-28 21:48
 * @Description
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
