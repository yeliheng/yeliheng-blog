package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Category;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {

    public int addCategory(Category category);

    public int updateCategory(Category category);

    public int checkIfExistArticle(Long categoryId);

    public List<Category> selectCategoryList(Category category);
}
