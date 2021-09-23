package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Category;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {

    public int addCategory(Category category);

    public int updateCategory(Category category);

    public int checkIfExistArticle(Long categoryId);
}
