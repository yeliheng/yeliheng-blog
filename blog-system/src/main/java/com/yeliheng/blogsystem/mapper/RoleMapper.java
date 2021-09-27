package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Role;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<Role> {

    int addRole(Role role);

    int updateRole(Role role);
}
