package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.entity.Category;

import java.util.List;

public interface ICategoryService {

    /**
     *
     * 新增分类
     * @param category 分类实体
     */
    void addCategory(Category category);

    /**
     *
     * 删除分类
     * @param categoryId 分类Id
     */
    void deleteCategory(Long categoryId);

    /**
     *
     * 修改分类
     * @param category 分类实体
     */
    void updateCategory(Category category);


    /**
     *
     * 获取所有分类
     * @return 分类实体列表
     */
    List<Category> getCategories(Category category);

    /**
     *
     * 获取所有分类(分页)
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 分类列表带分页
     */
    PageInfo<Category> getCategoriesPaged(Integer page, Integer pageSize,Category category);



}
