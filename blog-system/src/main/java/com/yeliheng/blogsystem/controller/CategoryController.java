package com.yeliheng.blogsystem.controller;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Category;
import com.yeliheng.blogsystem.service.ICategoryService;
import com.yeliheng.blogsystem.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping()
    public CommonResponse<Object> add(@Validated @RequestBody Category category){
        categoryService.addCategory(category);
        return CommonResponse.success();
    }

    @DeleteMapping()
    public CommonResponse<Object> delete(@RequestParam Long id){
        categoryService.deleteCategory(id);
        return CommonResponse.success();
    }

    @PutMapping()
    public CommonResponse<Object> update(@RequestBody Category category){
        categoryService.updateCategory(category);
        return CommonResponse.success();
    }

    @GetMapping()
    public CommonResponse<Object> getCategories(@RequestParam(value = "page",required = false) Integer page,
                                                @RequestParam(value = "pageSize",required = false) Integer pageSize,Category category){
        if(StringUtils.isNull(page) || StringUtils.isNull(pageSize))
             return CommonResponse.success(categoryService.getCategories(category));
        else return CommonResponse.success(categoryService.getCategoriesPaged(page,pageSize,category));
    }

}
