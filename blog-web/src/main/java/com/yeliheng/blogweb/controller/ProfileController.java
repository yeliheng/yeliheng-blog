package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.User;
import com.yeliheng.blogsystem.service.IUserService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
public class ProfileController {
    @Autowired
    private IUserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @PutMapping
    public CommonResponse<Object> updateProfile(@Validated @RequestBody User user) {
        userService.updateProfile(user);
        return CommonResponse.success();
    }

    @PutMapping("/resetPassword")
    public CommonResponse<Object> resetPassword(String oldPassword, String newPassword) {
        if(StringUtils.isNull(oldPassword) || StringUtils.isNull(newPassword))
            throw new RequestFormatException("密码输入不合法!");
        userService.resetPassword(oldPassword, newPassword);
        return CommonResponse.success();
    }
}
