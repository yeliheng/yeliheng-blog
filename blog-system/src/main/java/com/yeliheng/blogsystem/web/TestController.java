package com.yeliheng.blogsystem.web;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @PostMapping("test")
    public CommonResponse<Object> test() {
       // throw new UnauthorizedException("认证失败!");

        return CommonResponse.success(userMapper.selectUserByUserName("yeliheng"));
    }
}
