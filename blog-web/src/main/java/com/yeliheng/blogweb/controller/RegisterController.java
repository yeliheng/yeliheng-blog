package com.yeliheng.blogweb.controller;


import com.yeliheng.blogcommon.exception.NotPermittedException;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogsystem.domain.RegisterUser;
import com.yeliheng.blogsystem.service.IRegisterService;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "注册")
@RestController
@Validated
public class RegisterController {
    @Autowired
    private IRegisterService registerService;

    @Value("${blogsystem.allow-register}")
    private boolean allowRegister;

    @PostMapping("register")
    public CommonResponse<Object> register(@Valid RegisterUser registerUser){
        if(!allowRegister) throw new NotPermittedException("暂未开放用户注册");
        if(!registerUser.getPassword().equals(registerUser.getPassword_confirm()))
            throw new RequestFormatException("两次输入的密码不一致");
        registerService.register(registerUser);
        return CommonResponse.success();
    }
}
