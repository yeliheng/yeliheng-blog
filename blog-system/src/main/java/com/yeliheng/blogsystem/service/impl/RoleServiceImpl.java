package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.InternalServerException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Role;
import com.yeliheng.blogsystem.domain.RoleMenu;
import com.yeliheng.blogsystem.mapper.RoleMapper;
import com.yeliheng.blogsystem.mapper.RoleMenuMapper;
import com.yeliheng.blogsystem.mapper.UserRoleMapper;
import com.yeliheng.blogsystem.service.IRoleService;
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

    @Autowired
    private UserRoleMapper userRoleMapper;

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
     * 检查角色是否存在用户
     * @param roleId 角色Id
     */
    private void checkRoleUsed(Long roleId) {

    }

    /**
     * 删除角色
     *
     * @param roleId 角色Id
     */
    @Transactional
    @Override
    public void deleteRole(Long roleId) {
        if(userRoleMapper.checkRoleUser(roleId) > 0) {
            throw new GeneralException("删除失败，请解除所有用户与该角色的绑定");
        }
        int rows = roleMapper.deleteById(roleId);
        if(rows <= 0) throw new GeneralException("删除失败，角色可能不存在");
        //删除关联
        deleteRoleMenuByRoleId(roleId);
    }

    /**
     * 更新角色
     *
     * @param role 角色实体
     */
    @Transactional
    @Override
    public void updateRole(Role role) {
        int rows = roleMapper.updateRole(role);
        //删除关联
        deleteRoleMenuByRoleId(role.getId());
        //重新关联
        insertRoleMenu(role);
        if(rows <= 0) throw new GeneralException("更新失败，可能角色不存在");
    }

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<Role> getRoles() {
        return roleMapper.getRoleList(null);
    }

    /**
     * 获取分页的角色列表
     *
     * @param page     当前页
     * @param pageSize 一页多少
     * @param role     角色实体
     * @return 角色带分页信息
     */
    @Override
    public PageInfo<Role> getRolesPaged(Integer page, Integer pageSize, Role role) {
        PageHelper.startPage(page,pageSize);
        List<Role> roleList = roleMapper.getRoleList(role);
        return new PageInfo<>(roleList);
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
     *
     * 关联角色菜单
     * @param role 角色实体
     */
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
     *
     * 解除角色与菜单的绑定 通过角色Id
     * @param roleId 角色Id
     */
    public void deleteRoleMenuByRoleId(Long roleId){
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
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
