package com.yeliheng.blogsystem.controller;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.utils.UserUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "测试API ")
@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUtils userUtils;
    @PreAuthorize("@perm.hasPerm('aa:bb:cc')")
    @ResponseBody
    @PostMapping("test")
    public CommonResponse<Object> test() {
       // throw new UnauthorizedException("认证失败!");

        return CommonResponse.success(userUtils.getLoginUserId());
    }
}
