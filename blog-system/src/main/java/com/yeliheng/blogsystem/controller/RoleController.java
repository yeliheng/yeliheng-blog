package com.yeliheng.blogsystem.controller;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Role;
import com.yeliheng.blogsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping()
    public CommonResponse<Object> add(@Validated @RequestBody Role role){
        roleService.addRole(role);
        return CommonResponse.success();
    }

    @DeleteMapping()
    public CommonResponse<Object> delete(@RequestParam Long id){
        roleService.deleteRole(id);
        return CommonResponse.success();
    }

    @PutMapping()
    public CommonResponse<Object> update(@RequestBody Role role){
        roleService.updateRole(role);
        return CommonResponse.success();
    }

    @GetMapping()
    public CommonResponse<List<Role>> getRoles(){
        return CommonResponse.success(roleService.getRoles());
    }
}
