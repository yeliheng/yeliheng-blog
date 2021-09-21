package com.yeliheng.blogsystem.controller.article;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Category;
import com.yeliheng.blogsystem.service.ICategoryService;
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
    public CommonResponse<List<Category>> getCategories(){
        return CommonResponse.success(categoryService.getCategories());
    }
}
