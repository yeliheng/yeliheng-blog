package com.yeliheng.blogsystem.controller;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.LoginUser;
import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.service.IPermissionService;
import com.yeliheng.blogsystem.service.IUserService;
import com.yeliheng.blogsystem.utils.RedisUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private IPermissionService permissionService;


    @PostMapping
    public CommonResponse<Object> add(@Validated @RequestBody User user){
        userService.insertUser(user);
        return CommonResponse.success();
    }

    @PutMapping
    public CommonResponse<Object> update(@RequestBody User user){
        userService.updateUser(user);
        return CommonResponse.success();
    }


    @GetMapping("/info")
    public CommonResponse<Object> getUserInfo(){
        Map<String,Object> map = new HashMap<>();

        LoginUser loginUser = redisUtils.getCacheObject(userUtils.getLoginUserId().toString());
        //获取角色
        Set<String> roles = permissionService.getRolesByUser(loginUser.getUser());
        //获取角色权限
        Set<String> permissions = permissionService.getMenuPermission(loginUser.getUser());

        map.put("user",loginUser);
        map.put("role",roles);
        map.put("permissions",permissions);

        return CommonResponse.success(map);
    }
}