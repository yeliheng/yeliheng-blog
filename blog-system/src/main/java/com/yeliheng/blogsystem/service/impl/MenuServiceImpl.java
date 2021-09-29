package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.Menu;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.MenuMapper;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
