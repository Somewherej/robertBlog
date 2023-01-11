package com.robertBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robertBlog.domain.entity.Menu;

import java.util.List;


/**
 * @author Somewherej
 * @Date 2022-12-15 17:47
 * @Description
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectMenuList(Menu menu);

    boolean hasChild(Long menuId);

    List<Long> selectMenuListByRoleId(Long roleId);
}