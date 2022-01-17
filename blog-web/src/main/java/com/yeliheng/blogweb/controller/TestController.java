package com.yeliheng.blogweb.controller;



import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogframework.config.FrameworkConfig;
import com.yeliheng.blogframework.storage.Storage;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.utils.UserUtils;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public CommonResponse<Object> test(@RequestParam("file")MultipartFile file){
        String[] allowedExt = {"png","jpg","jpeg","gif"}; //设置允许的后缀
        Storage storage = new Storage("avatar"); //新建一个存储器
        storage.upload(file,userUtils.getLoginUserId().toString(),allowedExt); //上传！

        return CommonResponse.success();
    }
}
