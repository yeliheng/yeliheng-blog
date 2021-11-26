package com.yeliheng.blogsystem.controller;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Role;
import com.yeliheng.blogsystem.service.IRoleService;
import com.yeliheng.blogsystem.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PreAuthorize("@perm.hasPerm('admin:roles:add')")
    @PostMapping("/admin/roles")
    public CommonResponse<Object> add(@Validated @RequestBody Role role){
        roleService.addRole(role);
        return CommonResponse.success();
    }
    @PreAuthorize("@perm.hasPerm('admin:roles:delete')")
    @DeleteMapping("/admin/roles")
    public CommonResponse<Object> delete(@RequestParam Long id){
        roleService.deleteRole(id);
        return CommonResponse.success();
    }
    @PreAuthorize("@perm.hasPerm('admin:roles:edit')")
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
