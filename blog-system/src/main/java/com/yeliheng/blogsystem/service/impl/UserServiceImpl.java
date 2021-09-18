package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User selectUserByUserName(String username) {
        return userMapper.selectUserByUserName(username);
    }

    @Override
    public Boolean checkUsernameUnique(String username) {
        return false;
    }

    @Override
    @Transactional
    public Integer insertUser(User user) {
        Integer rows = userMapper.insertUser(user);
        //TODO: 添加角色关联
        return rows;
    }

    @Override
    public Boolean registerUser(User user) {
        return userMapper.insertUser(user) > 0;
    }
}
