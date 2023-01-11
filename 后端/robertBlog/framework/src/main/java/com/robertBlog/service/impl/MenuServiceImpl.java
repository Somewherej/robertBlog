package com.robertBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robertBlog.constants.SystemConstants;
import com.robertBlog.domain.entity.Menu;
import com.robertBlog.mapper.MenuMapper;
import com.robertBlog.service.MenuService;
import com.robertBlog.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;



/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Somewherej
 * @since 2022-11-26 21:21:36
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    //函数说明:   根据用户的ID查询所有的权限信息
    //permissions中需要所有类型为C或者F(C是菜单,F是按钮)       状态为正常,且未被删除的权限
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        //getBaseMapper其实是MenuMapper类型的, 我们也要在MenuMapper中自定义一个方法来查询非管理员的所有权限信息
        return getBaseMapper().selectPermsByUserId(id);
    }




    //函数说明:   根据用户ID 查询角色信息  Then查询所有的menu信息
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //查询所有符合要求(没有删除)的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则获取当前用户所处角色下所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //因为菜单是层级关系,因此我们要构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        //因为根的序号就是0
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }




    //函数说明: 根据这个用户表去封装一个层级关系
    //找出第一层菜单,然后再把它们的子菜单设置到children属性中
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                //第一层筛选一次   找到符合条件的根节点
                .filter(menu -> menu.getParentId().equals(parentId))
                //第二层再进行筛选  找到符合条件的子菜单
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                //收集List集合
                .collect(Collectors.toList());
        return menuTree;
    }

    //函数说明:
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                //使用递归
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }


    @Override
    public List<Menu> selectMenuList(Menu menu) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        return menus;
    }

    @Override
    public boolean hasChild(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return count(queryWrapper) != 0;
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }



    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */

}


