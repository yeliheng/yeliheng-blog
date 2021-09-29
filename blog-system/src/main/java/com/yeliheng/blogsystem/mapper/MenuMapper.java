package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

public interface MenuMapper extends Mapper<Menu> {
    int addMenu(Menu menu);

    int updateMenu(Menu menu);

}
