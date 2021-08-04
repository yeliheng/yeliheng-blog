package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UnauthorizedException {
        User user = userMapper.selectUserByUserName(username);
        if(user.toString().isEmpty()){
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }
        return new User(user);
    }
}
