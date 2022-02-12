package com.yeliheng.blogweb.controller;


import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.BusinessType;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Role;
import com.yeliheng.blogsystem.service.IRoleService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PreAuthorize("@perm.hasPerm('admin:roles:add')")
    @Log(moduleName = "添加角色",businessType = BusinessType.INSERT)
    @PostMapping("/admin/roles")
    public CommonResponse<Object> add(@Validated @RequestBody Role role){
        roleService.addRole(role);
        return CommonResponse.success();
    }
    @PreAuthorize("@perm.hasPerm('admin:roles:delete')")
    @Log(moduleName = "删除角色",businessType = BusinessType.DELETE)
    @DeleteMapping("/admin/roles")
    public CommonResponse<Object> delete(@RequestParam Long id){
        roleService.deleteRole(id);
        return CommonResponse.success();
    }
    @PreAuthorize("@perm.hasPerm('admin:roles:edit')")
    @Log(moduleName = "修改角色",businessType = BusinessType.UPDATE)
    @PutMapping("/admin/roles")
    public CommonResponse<Object> update(@RequestBody Role role){
        roleService.updateRole(role);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:roles:list')")
    @GetMapping("/admin/roles")
    public CommonResponse<Object> getRoles(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            Role role
            ){
        if(StringUtils.isNull(page) || StringUtils.isNull(pageSize)){
            return CommonResponse.success(roleService.getRoles());
        }else {
            return CommonResponse.success(roleService.getRolesPaged(page,pageSize,role));
        }

    }
}
