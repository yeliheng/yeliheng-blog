package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.User;

import java.util.Set;

public interface IPermissionService {
    /**
     *
     * 获取菜单数据权限
     *
     * @param user 用户实体
     * @return 菜单权限集合
     */
    Set<String> getMenuPermission(User user);

}
