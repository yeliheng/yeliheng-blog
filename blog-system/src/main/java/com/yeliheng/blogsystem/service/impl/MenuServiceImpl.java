package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.InternalServerException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Menu;
import com.yeliheng.blogsystem.domain.Router;
import com.yeliheng.blogsystem.dto.MenuTreeDTO;

import com.yeliheng.blogsystem.mapper.MenuMapper;
import com.yeliheng.blogsystem.service.IMenuService;

import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserUtils userUtils;

    /**
     * 新增菜单
     *
     * @param menu 菜单实体
     */
    @Override
    public void addMenu(Menu menu) {
       menu.setUserId(userUtils.getLoginUserId());
       int rows = menuMapper.addMenu(menu);
       if(rows <= 0) throw new InternalServerException("新增菜单失败，未知错误");

    }

    /**
     * 更新菜单
     *
     * @param menu 菜单实体
     */
    @Override
    public void updateMenu(Menu menu) {
       int rows = menuMapper.updateMenu(menu);
       if(rows <= 0) throw new GeneralException("删除失败，菜单可能不存在");
    }

    /**
     * 通过id删除菜单
     *
     * @param menuId 菜单id
     */
    @Override
    public void deleteMenuById(Long menuId) {
        if(menuMapper.checkMenuAllowed(menuId) > 0)
            throw new GeneralException("删除失败，请先删除所有子菜单");
        int rows = menuMapper.deleteById(menuId);
        if(rows <= 0) throw new GeneralException("删除失败，菜单可能不存在");
    }

    /**
     * 获取菜单列表
     *
     * @param menu 菜单实体
     * @return 菜单列表
     */
    @Override
    public List<Menu> getMenuList(Menu menu) {
        return menuMapper.getMenuList(menu);
    }

    /**
     * 获取前端选择所需的菜单树
     * @return 菜单树形列表
     */
    @Override
    public List<MenuTreeDTO> getMenuTree() {
        List<Menu> menuTree = buildMenuTree(menuMapper.getMenus());
        return menuTree.stream().map(MenuTreeDTO::new).collect(Collectors.toList());
    }

    /**
     * 构建出前端所需的菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树形列表
     */
    @Override
    public List<Menu> buildMenuTree(List<Menu> menuList) {
        List<Menu> resultList = new ArrayList<>();
        List<Long> temp = new ArrayList<>();
        menuList.forEach(Menu -> temp.add(Menu.getId()));

        for (Menu menu : menuList) {
            if (!temp.contains(menu.getParentId())) {
                recursionFn(menuList, menu);
                resultList.add(menu);
            }
        }

        if(resultList.isEmpty())
            resultList = menuList;
        return resultList;
    }


    /**
     * 通过用户id查询菜单权限
     *
     * @param userId 用户Id
     * @return 权限集合
     */
    @Override
    public Set<String> getMenuPermissionByUserId(Long userId) {
        List<String> perms = menuMapper.getMenuPermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for(String perm : perms){
            if(StringUtils.isNotEmpty(perm)){
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 通过用户id获取菜单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> getMenusByUserId(Long userId) {
        List<Menu> menuList = null;
        if(userId == 1){
            menuList = menuMapper.getMenus();
        }else {
            menuList = menuMapper.getMenusByUserId(userId);
        }
        return getChildPerms(menuList,0);
    }

    public List<Menu> getChildPerms(List<Menu> list,int parentId){
        List<Menu> treeList = new ArrayList<>();
        for (Menu t : list) {
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                treeList.add(t);
            }
        }
        return treeList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<Menu> list, Menu t)
    {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, t);
        t.setChildren(childList);
        childList.stream()
                .filter(Menu -> hasChild(list, Menu))
                .forEach(Menu -> {recursionFn(list, Menu);});
    }

    /**
     * 得到子节点列表
     */
    private List<Menu> getChildList(List<Menu> list, Menu t)
    {
        List<Menu> tlist = new ArrayList<>();
        for (Menu n : list) {
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Menu> list, Menu t)
    {
        return getChildList(list, t).size() > 0;
    }


    public List<Router> buildMenus(List<Menu> menuList){
        List<Router> routerList = new LinkedList<>();

        for(Menu menu : menuList) {
            Router router = new Router();
            router.setName(menu.getMenuName());
            router.setPath(menu.getPath());
            router.setComponent(menu.getComponent());
            router.setIcon(menu.getIcon());
            router.setPermission(menu.getPermission());
            router.setHidden(menu.getHidden());
            List<Menu> childMenus = menu.getChildren();

            if (!childMenus.isEmpty()) {
                router.setChildren(buildMenus(childMenus));
            }

            routerList.add(router);
        }
        return routerList;
    }

    /**
     * 通过角色Id获取菜单Id
     * @param roleId 角色id
     * @return 菜单id
     */
    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return menuMapper.getMenuIdsByRoleId(roleId);
    }

}
