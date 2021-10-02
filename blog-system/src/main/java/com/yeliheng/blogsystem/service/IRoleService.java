package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.Role;

import java.util.List;
import java.util.Set;

public interface IRoleService {
    /**
     *
     * 新增角色
     * @param role 角色实体
     */
    void addRole(Role role);


    /**
     *
     * 删除角色
     * @param roleId 角色Id
     */
    void deleteRole(Long roleId);

    /**
     *
     * 更新角色
     * @param role 角色实体
     */
    void updateRole(Role role);

    /**
     *
     * 获取所有角色
     * @return 角色列表
     */
    List<Role> getRoles();

    /**
     *
     * 通过用户Id获取角色
     * @param userId 用户Id
     * @return 角色列表
     */
    Set<String> getRolesByUserId(Long userId);

    /**
     *
     * 检查角色名称是否唯一
     * @param role 角色实体
     * @return 角色名称
     */
    String checkRoleNameUnique(Role role);

    /**
     *
     * 检查角色标识符是否唯一
     * @param role 角色实体
     * @return 角色名称
     */
    String checkRoleCharUnique(Role role);
}
