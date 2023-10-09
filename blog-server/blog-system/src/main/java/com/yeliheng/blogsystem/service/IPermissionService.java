package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.domain.User;

import java.util.Set;

public interface IPermissionService {


    /**
     *
     * 获取用户的角色权限
     * @param user 用户实体
     * @return 角色权限集合
     */
    Set<String> getRolesByUser(User user);

    /**
     *
     * 获取菜单数据权限
     *
     * @param user 用户实体
     * @return 菜单权限集合
     */
    Set<String> getMenuPermission(User user);

}
