package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Category;

import java.util.List;

public interface CategoryMapper {

    public int addCategory(Category category);

    public int updateCategory(Category category);

    public int countArticles(Long categoryId);

    public int existsById(Long categoryId);

    public int deleteById(Long categoryId);

    public List<Category> selectCategoryList(Category category);

    public int countCategories();

}
