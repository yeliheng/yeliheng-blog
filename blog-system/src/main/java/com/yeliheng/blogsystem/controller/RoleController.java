package com.yeliheng.blogsystem.controller;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Role;
import com.yeliheng.blogsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/admin/roles")
    public CommonResponse<Object> add(@Validated @RequestBody Role role){
        roleService.addRole(role);
        return CommonResponse.success();
    }

    @DeleteMapping("/admin/roles")
    public CommonResponse<Object> delete(@RequestParam Long id){
        roleService.deleteRole(id);
        return CommonResponse.success();
    }

    @PutMapping("/admin/roles")
    public CommonResponse<Object> update(@RequestBody Role role){
        roleService.updateRole(role);
        return CommonResponse.success();
    }

    @GetMapping("/admin/roles")
    public CommonResponse<List<Role>> getRoles(){
        return CommonResponse.success(roleService.getRoles());
    }
}
