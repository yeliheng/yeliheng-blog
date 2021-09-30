package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogsystem.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IMenuService menuService;
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
