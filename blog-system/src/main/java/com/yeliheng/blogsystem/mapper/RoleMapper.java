package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Role;

import java.util.List;

public interface RoleMapper {

    int addRole(Role role);

    int updateRole(Role role);

    int deleteById(Long roleId);

    List<Role> getRolesByUserId(Long userId);

    Long checkRoleNameUnique(String roleName);

    Long checkRoleCharUnique(String roleChar);

    List<Role> getRoleList(Role role);
}
