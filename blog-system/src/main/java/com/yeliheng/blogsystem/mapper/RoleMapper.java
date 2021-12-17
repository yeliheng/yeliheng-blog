package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    int addRole(Role role);

    int updateRole(Role role);

    List<Role> getRolesByUserId(Long userId);

    Long checkRoleNameUnique(String roleName);

    Long checkRoleCharUnique(String roleChar);

    List<Role> getRoleList(Role role);
}
