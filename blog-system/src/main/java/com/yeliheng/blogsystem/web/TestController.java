package com.yeliheng.blogsystem.web;


import com.yeliheng.blogsystem.common.CommonResponse;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @ResponseBody
    @PostMapping("test")
    public CommonResponse<Object> test() {
        return CommonResponse.success();
    }
}
