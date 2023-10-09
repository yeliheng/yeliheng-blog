package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogcommon.exception.UnauthorizedException;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.domain.User;
import com.yeliheng.blogsystem.service.IPermissionService;
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

    @Autowired
    private IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UnauthorizedException {
        User user = userService.selectUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user){
        return new LoginUser(user,permissionService.getMenuPermission(user));
    }
}
