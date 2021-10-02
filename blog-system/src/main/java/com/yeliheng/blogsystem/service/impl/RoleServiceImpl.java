package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.Role;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.RoleMapper;
import com.yeliheng.blogsystem.service.IRoleService;
import com.yeliheng.blogsystem.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 新增角色
     *
     * @param role 角色实体
     */
    @Override
    public void addRole(Role role) {
        int rows = roleMapper.addRole(role);
        if(rows <= 0) throw new InternalServerException("添加失败，未知错误");
    }

    /**
     * 删除角色
     *
     * @param roleId 角色Id
     */
    @Override
    public void deleteRole(Long roleId) {
        int rows = roleMapper.deleteByPrimaryKey(roleId);
        if(rows <= 0) throw new GeneralException("删除失败，角色可能不存在");
    }

    /**
     * 更新角色
     *
     * @param role 角色实体
     */
    @Override
    public void updateRole(Role role) {
        int rows = roleMapper.updateRole(role);
        if(rows <= 0) throw new GeneralException("更新失败，可能角色不存在");
    }

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<Role> getRoles() {
        return roleMapper.selectAll();
    }

    /**
     * 通过用户Id获取角色
     *
     * @param userId 用户Id
     * @return 角色列表
     */
    @Override
    public Set<String> getRolesByUserId(Long userId) {
        List<Role> roleList = roleMapper.getRolesByUserId(userId);
        Set<String> roleSet = new HashSet<>();
        for (Role role : roleList){
            if(StringUtils.isNotNull(role)){
                roleSet.addAll(Arrays.asList(role.getRoleChar().trim().split(",")));
            }
        }
        return roleSet;
    }

    /**
     * 检查角色名称是否唯一
     *
     * @param role 角色实体
     * @return 角色名称
     */
    @Override
    public String checkRoleNameUnique(Role role) {
        return null;
    }

    /**
     * 检查角色标识符是否唯一
     *
     * @param role 角色实体
     * @return 角色名称
     */
    @Override
    public String checkRoleCharUnique(Role role) {
        return null;
    }
}
