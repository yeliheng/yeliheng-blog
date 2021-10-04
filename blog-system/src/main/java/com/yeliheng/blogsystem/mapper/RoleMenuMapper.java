package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper {

    int batchRoleMenu(List<RoleMenu> roleMenuList);

    int deleteRoleMenuByRoleId(Long roleId);

}
