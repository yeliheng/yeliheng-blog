package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {

    int addMenu(Menu menu);

    int updateMenu(Menu menu);

    List<String> getMenuPermissionByUserId(Long userId);

    List<Menu> getMenusByUserId(Long userId);

    List<Menu> getMenus();
}
