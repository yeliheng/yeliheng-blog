package com.yeliheng.blogsystem.web;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "测试API ")
@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @PostMapping("test")
    public CommonResponse<Object> test(@RequestParam("username") String username) {
       // throw new UnauthorizedException("认证失败!");

        return CommonResponse.success(userMapper.selectUserByUserName(username));
    }
}
