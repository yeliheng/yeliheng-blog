package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.Menu;

import java.util.List;
import java.util.Set;

public interface IMenuService {

    /**
     *
     * 新增菜单
     * @param menu 菜单实体
     */
    void addMenu(Menu menu);


    /**
     *
     * 更新菜单
     * @param menu 菜单实体
     */
    void updateMenu(Menu menu);

    /**
     *
     * 通过id删除菜单
     * @param menuId 菜单id
     */
    void deleteMenuById(Long menuId);

    /**
     *
     * 获取所有菜单
     * @return 菜单列表
     */
    List<Menu> getMenus();

    /**
     *
     * 通过用户id查询菜单权限
     * @param userId 用户Id
     * @return 权限集合
     */
    Set<String> getMenuPermissionByUserId(Long userId);

}
