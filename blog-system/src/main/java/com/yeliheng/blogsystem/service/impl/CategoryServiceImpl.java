package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.Category;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.CategoryMapper;
import com.yeliheng.blogsystem.service.ICategoryService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserUtils userUtils;

    /**
     * 新增分类
     *
     * @param category 分类实体
     */
    @Override
    public void addCategory(Category category) {
        category.setUserId(userUtils.getLoginUserId());
        int result = categoryMapper.addCategory(category);
        if(result <= 0) throw new InternalServerException("新建分类失败，未知错误");
    }

    /**
     * 删除分类
     *
     * @param categoryId 分类Id
     */
    @Override
    public void deleteCategory(Long categoryId) {
        int result = categoryMapper.deleteByPrimaryKey(categoryId);
        if(result <= 0) throw new GeneralException("删除失败，分类可能不存在");
    }

    /**
     * 修改分类
     *
     * @param category 分类实体
     */
    @Override
    public void updateCategory(Category category) {
        int result = categoryMapper.updateCategory(category);
        if(result <= 0) throw new GeneralException("更新失败，分类可能不存在");
    }

    /**
     * 获取所有分类
     *
     * @return 分类实体列表
     */
    @Override
    public List<Category> getCategories() {
        return categoryMapper.selectAll();
    }
}
