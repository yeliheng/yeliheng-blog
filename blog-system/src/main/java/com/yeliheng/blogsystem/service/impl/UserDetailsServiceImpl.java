package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.LoginUser;
import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.exception.UnauthorizedException;
import com.yeliheng.blogsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UnauthorizedException {
        User user = userService.selectUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }
        // 存放权限
       // List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user){
        return new LoginUser(user.getId(),user);
    }
}
