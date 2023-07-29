package com.yeliheng.blogweb.controller;



import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.OperateType;
import com.yeliheng.blogsystem.domain.Menu;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @PreAuthorize("@perm.hasPerm('admin:menus:add')")
    @Log(moduleName = "添加菜单",operateType = OperateType.INSERT)
    @PostMapping("/admin/menus")
    public CommonResponse<Object> add(@Validated @RequestBody Menu menu){
        menuService.addMenu(menu);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:delete')")
    @Log(moduleName = "删除菜单",operateType = OperateType.DELETE)
    @DeleteMapping("/admin/menus")
    public CommonResponse<Object> delete(@RequestParam Long id){
        menuService.deleteMenuById(id);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:edit')")
    @Log(moduleName = "修改菜单",operateType = OperateType.UPDATE)
    @PutMapping("/admin/menus")
    public CommonResponse<Object> update(@RequestBody Menu menu){
        menuService.updateMenu(menu);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:menus:view')")
    @GetMapping("/admin/menus")
    public CommonResponse<Object> get(Menu menu){
        return CommonResponse.success(menuService.getMenuList(menu));
    }

    @GetMapping("/admin/menus/tree")
    public CommonResponse<Object> getMenuTree() {
            return CommonResponse.success(menuService.getMenuTree());
    }

    @GetMapping("/admin/menus/getByRoleId")
    public CommonResponse<Object> getMenuIdsByRoleId(@RequestParam Long id) {
        return CommonResponse.success(menuService.getMenuIdsByRoleId(id));
    }


}
