package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.BusinessType;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogframework.storage.FileSystemAdapter;
import com.yeliheng.blogframework.storage.LocalStorage;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.domain.User;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.service.IUserService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users/profile")
public class ProfileController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserMapper userMapper;

    /**
     * 更新用户信息
     * @param user 用户实体
     */
    @PutMapping
    @Log(moduleName = "修改个人信息",businessType = BusinessType.UPDATE)
    public CommonResponse<Object> updateProfile(@Validated @RequestBody User user) {
        userService.updateProfile(user);
        return CommonResponse.success();
    }

    /**
     * 重置密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @PutMapping("/resetPassword")
    @Log(moduleName = "修改密码",businessType = BusinessType.UPDATE)
    public CommonResponse<Object> resetPassword(String oldPassword, String newPassword) {
        if(StringUtils.isNull(oldPassword) || StringUtils.isNull(newPassword))
            throw new RequestFormatException("密码输入不合法!");
        userService.resetPassword(oldPassword, newPassword);
        return CommonResponse.success();
    }

    /**
     * 设置用户头像
     * @param file 文件
     */
    @PostMapping("/avatar")
    @Log(moduleName = "设置头像",businessType = BusinessType.INSERT)
    public CommonResponse<Object> setAvatar(MultipartFile file) {
        if(StringUtils.isNull(file) || file.isEmpty()) throw new RequestFormatException("文件不能为空!");
        String[] allowedExt = {"png","jpg","jpeg","gif"}; //设置允许的后缀
        LocalStorage localStorage = new LocalStorage("avatar"); //新建一个存储器
        FileSystemAdapter adapter = new FileSystemAdapter(localStorage);
        String path = adapter.getFileSystem().handleFile(file,userUtils.getLoginUserId().toString(),allowedExt);
        if(StringUtils.isEmpty(path))
            throw new UnexpectedException();
        User user = new User();
        user.setAvatar(path);
        userService.updateProfile(user);
        //刷新缓存
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        //刷新缓存
        loginUser.setUser(userMapper.selectUserByUserId(loginUser.getUser().getId()));
        tokenUtils.refreshLoginUser(loginUser);
        return CommonResponse.success(path);
    }
}
