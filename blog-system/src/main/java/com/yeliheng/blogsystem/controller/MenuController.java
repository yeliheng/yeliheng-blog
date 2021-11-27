package com.yeliheng.blogsystem.controller;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Category;
import com.yeliheng.blogsystem.entity.Menu;
import com.yeliheng.blogsystem.entity.Role;
import com.yeliheng.blogsystem.service.ICategoryService;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogsystem.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @PreAuthorize("@perm.hasPerm('admin:menus:add')")
    @PostMapping("/admin/menus")
    public CommonResponse<Object> add(@Validated @RequestBody Menu menu){
        menuService.addMenu(menu);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:delete')")
    @DeleteMapping("/admin/menus")
    public CommonResponse<Object> delete(@RequestParam Long id){
        menuService.deleteMenuById(id);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:edit')")
    @PutMapping("/admin/menus")
    public CommonResponse<Object> update(@RequestBody Menu menu){
        menuService.updateMenu(menu);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:list')")
    @GetMapping("/admin/menus/tree")
    public CommonResponse<Object> getMenuTree() {
            return CommonResponse.success(menuService.getMenuTree());
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:list')")
    @GetMapping("/admin/menus/getByRoleId")
    public CommonResponse<Object> getMenuIdsByRoleId(@RequestParam Long id) {
        return CommonResponse.success(menuService.getMenuIdsByRoleId(id));
    }


}
