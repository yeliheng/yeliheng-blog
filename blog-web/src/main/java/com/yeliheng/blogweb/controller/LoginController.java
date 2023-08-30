package com.yeliheng.blogweb.controller;


import com.baomidou.kaptcha.Kaptcha;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.LoginBody;
import com.yeliheng.blogsystem.domain.RefreshToken;
import com.yeliheng.blogsystem.service.ILoginService;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


@Tag(name = "登录")
@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private Kaptcha kaptcha;

    @Value("${system.captcha-enabled}")
    private boolean captchaEnabled;

    @PostMapping("/login")
    public CommonResponse<Object> login(@RequestBody @Validated LoginBody loginBody){
        if(captchaEnabled) {
            if(StringUtils.isNull(loginBody.getCode())) {
                throw new RequestFormatException("验证码不能为空!");
            }
            kaptcha.validate(loginBody.getCode());
        }

        Map<String,String> token = loginService.login(loginBody.getUsername(),loginBody.getPassword(),loginBody.isRememberMe());
        return CommonResponse.success(token);
    }

    @PostMapping("/refreshToken")
    public CommonResponse<Object> refreshToken(@RequestBody @Validated RefreshToken refreshToken) {
        String token = loginService.refreshToken(refreshToken.getToken());
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("token",token);
        return CommonResponse.success(resultMap);
    }
}
