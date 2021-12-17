package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.UserRole;

import java.util.List;

public interface UserRoleMapper {
    public int batchUserRole(List<UserRole> userRoleList);

    public int deleteByUserId(Long userId);

    public int checkRoleUser(Long roleId);
}
