package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.Role;
import com.yeliheng.blogsystem.entity.RoleMenu;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.RoleMapper;
import com.yeliheng.blogsystem.mapper.RoleMenuMapper;
import com.yeliheng.blogsystem.service.IRoleService;
import com.yeliheng.blogsystem.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 新增角色
     *
     * @param role 角色实体
     */
    @Transactional
    @Override
    public void addRole(Role role) {
        if(!checkRoleNameUnique(role.getRoleName()))
            throw new GeneralException("角色名称已存在");
        if(!checkRoleCharUnique(role.getRoleChar()))
            throw new GeneralException("角色标识符已存在");
        int rows = roleMapper.addRole(role);
        if(rows <= 0) throw new InternalServerException("添加失败，未知错误");
        insertRoleMenu(role); //关联角色与菜单
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

    public void insertRoleMenu(Role role) {
        Long[] menuIds = role.getMenuIds();
        if (StringUtils.isNotNull(menuIds)) {
            List<RoleMenu> list = new ArrayList<>();
            for (Long menuId : role.getMenuIds()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(role.getId());
                roleMenu.setMenuId(menuId);
                list.add(roleMenu);
            }
            if (list.size() > 0) {
                int rows = roleMenuMapper.batchRoleMenu(list);
                if (rows <= 0) throw new InternalServerException("关联角色失败，未知错误");
            }
        }
    }

    /**
     * 检查角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色名称
     */
    @Override
    public Boolean checkRoleNameUnique(String roleName) {
        Long roleId = roleMapper.checkRoleNameUnique(roleName);
        return StringUtils.isNull(roleId);
    }

    /**
     * 检查角色标识符是否唯一
     *
     * @param roleChar 角色标识
     * @return 角色名称
     */
    @Override
    public Boolean checkRoleCharUnique(String roleChar) {
        Long roleId = roleMapper.checkRoleCharUnique(roleChar);
        return StringUtils.isNull(roleId);
    }
}
