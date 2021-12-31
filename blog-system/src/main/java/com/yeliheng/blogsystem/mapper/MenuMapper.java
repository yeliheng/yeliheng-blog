package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Menu;

import java.util.List;

public interface MenuMapper {

    int addMenu(Menu menu);

    int updateMenu(Menu menu);

    int deleteById(Long menuId);

    List<String> getMenuPermissionByUserId(Long userId);

    List<Menu> getMenusByUserId(Long userId);

    List<Menu> getMenus();

    List<Long> getMenuIdsByRoleId(Long roleId);
}
