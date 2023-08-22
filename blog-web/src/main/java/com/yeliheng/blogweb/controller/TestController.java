package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.config.LocalStorageConfig;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogframework.storage.FileSystem;
import com.yeliheng.blogframework.storage.FileUtils;
import com.yeliheng.blogframework.storage.adapter.AliOssStorageAdapter;
import com.yeliheng.blogframework.storage.adapter.LocalStorageAdapter;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.utils.UserUtils;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.FilenameUtils;
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
    public CommonResponse<Object> test(@RequestParam("file") MultipartFile file) {
        if (StringUtils.isNull(file) || file.isEmpty()) {
            throw new RequestFormatException("文件不能为空!");
        }

        String fileName = FileUtils.encodeFileName(FilenameUtils.getExtension(file.getOriginalFilename()));
        String filePath = String.format("%s/%s", "avatar", fileName);

        // 文件存储在本地
        AliOssStorageAdapter ossStorageAdapter = new AliOssStorageAdapter("yeliheng-test");
        FileSystem fileSystem = new FileSystem(ossStorageAdapter);

        try {
            fileSystem.write(file, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException();
        }
        return CommonResponse.success();
    }

}
