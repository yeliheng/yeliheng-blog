package com.yeliheng.blogsystem.controller;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
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
}
