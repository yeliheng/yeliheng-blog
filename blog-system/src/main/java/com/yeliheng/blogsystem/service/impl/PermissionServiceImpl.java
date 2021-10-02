package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogsystem.service.IPermissionService;
import com.yeliheng.blogsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleService roleService;

    /**
     * 获取用户的角色权限
     *
     * @param user 用户实体
     * @return 角色权限集合
     */
    @Override
    public Set<String> getRolesByUser(User user) {
        Set<String> roles = new HashSet<>();
        if(user.isAdmin())
            roles.add("admin");
        else
            roles.addAll(roleService.getRolesByUserId(user.getId()));
        return roles;
    }

    /**
     *
     * 获取菜单数据权限
     *
     * @param user 用户实体
     * @return 菜单权限集合
     */
    @Override
    public Set<String> getMenuPermission(User user) {
        Set<String> permissions = new HashSet<>();
        if(user.isAdmin()){
            permissions.add("*:*:*");
        }else {
            permissions.addAll(menuService.getMenuPermissionByUserId(user.getId()));
        }
        return permissions;
    }
}
