package com.yeliheng.blogweb.controller;

import com.yeliheng.blogframework.storage.FileSystemAdapter;
import com.yeliheng.blogframework.storage.OssStorage;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.utils.UserUtils;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "测试API")
@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUtils userUtils;

    @PreAuthorize("@perm.hasPerm('aa:bb:cc')")
    @ResponseBody
    @PostMapping("test")
    public CommonResponse<Object> test(@RequestParam("file")MultipartFile multipartFile){
        String[] allowedExt = {"png","jpg","jpeg","gif"}; //设置允许的后缀
        OssStorage ossStorage = new OssStorage(); //新建一个存储器
        FileSystemAdapter adapter = new FileSystemAdapter(ossStorage);
        return CommonResponse.success(adapter.getFileSystem().handleFile(multipartFile,"y-network",userUtils.getLoginUserId().toString(),allowedExt));
    }
}
