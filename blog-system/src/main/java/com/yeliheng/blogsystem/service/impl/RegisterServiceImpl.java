package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.RegisterUser;
import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.exception.UnexpectedException;
import com.yeliheng.blogsystem.service.IRegisterService;
import com.yeliheng.blogsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    private IUserService userService;

    @Override
    public Boolean register(RegisterUser registerUser) {
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(this.encryptPassword(registerUser.getPassword()));
        Boolean regFlag = userService.registerUser(user);
        if(!regFlag)
            throw new UnexpectedException();
        else return true;
    }

    //生成强哈希密码
    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
