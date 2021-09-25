package com.yeliheng.blogsystem.controller;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.service.ILoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;


@Api(tags = "登录")
@RestController
@Validated
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    public CommonResponse<Object> login( @RequestParam("username")  @NotBlank String username,
                                         @RequestParam("password")  @NotBlank String password){
        String token = loginService.login(username,password);
        return CommonResponse.success(token);
    }
}
