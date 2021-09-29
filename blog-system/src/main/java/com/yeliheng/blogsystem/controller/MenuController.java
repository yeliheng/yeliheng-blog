package com.yeliheng.blogsystem.controller;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Category;
import com.yeliheng.blogsystem.entity.Menu;
import com.yeliheng.blogsystem.service.ICategoryService;
import com.yeliheng.blogsystem.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @PostMapping
    public CommonResponse<Object> add(@Validated @RequestBody Menu menu){
        menuService.addMenu(menu);
        return CommonResponse.success();
    }

    @DeleteMapping
    public CommonResponse<Object> delete(@RequestParam Long id){
        menuService.deleteMenuById(id);
        return CommonResponse.success();
    }

    @PutMapping
    public CommonResponse<Object> update(@RequestBody Menu menu){
        menuService.updateMenu(menu);
        return CommonResponse.success();
    }

    @GetMapping
    public CommonResponse<List<Menu>> getMenus(){
        return CommonResponse.success(menuService.getMenus());
    }
}
