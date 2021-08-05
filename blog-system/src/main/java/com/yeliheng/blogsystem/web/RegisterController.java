package com.yeliheng.blogsystem.web;

import com.sun.istack.internal.NotNull;
import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.RegisterUser;
import com.yeliheng.blogsystem.exception.RequestFormatException;
import com.yeliheng.blogsystem.service.IRegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "注册")
@RestController
@Validated
public class RegisterController {
    @Autowired
    private IRegisterService registerService;

    @PostMapping("register")
    public CommonResponse<Object> register(@Valid RegisterUser registerUser){
        if(!registerUser.getPassword().equals(registerUser.getPassword_confirm()))
            throw new RequestFormatException("两次输入的密码不一致");
        registerService.register(registerUser);
        return CommonResponse.success();
    }
}
