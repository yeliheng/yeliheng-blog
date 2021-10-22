package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.Menu;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.MenuMapper;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogsystem.utils.StringUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

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
        int rows = menuMapper.deleteByPrimaryKey(menuId);
        if(rows <= 0) throw new GeneralException("删除失败，菜单可能不存在");
    }

    /**
     * 获取所有菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<Menu> getMenus() {
        return menuMapper.selectAll();
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
        //TODO: 构建前端所需的菜单树
        return getChildPerms(menuList,0);
    }

    public List<Menu> getChildPerms(List<Menu> list,int parentId){
        List<Menu> treeList = new ArrayList<>();
        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext();){
            Menu t = (Menu) iterator.next();
            if(t.getParentId() == parentId){
                recursionFn(list,t);
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
        for (Menu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Menu> getChildList(List<Menu> list, Menu t)
    {
        List<Menu> tlist = new ArrayList<Menu>();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext())
        {
            Menu n = (Menu) it.next();
            if (n.getParentId().longValue() == t.getId().longValue())
            {
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
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
