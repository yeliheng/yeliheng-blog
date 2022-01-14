package com.yeliheng.blogweb.controller;


import com.yeliheng.blogsystem.domain.LoginBody;
import com.yeliheng.blogsystem.service.ILoginService;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "登录")
@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    public CommonResponse<Object> login(@RequestBody @Validated LoginBody loginBody){
        String token = loginService.login(loginBody.getUsername(),loginBody.getPassword());
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("token",token);
        return CommonResponse.success(resultMap);
    }
}
