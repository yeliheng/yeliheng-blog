package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.exception.UnauthorizedException;
import com.yeliheng.blogsystem.service.ILoginService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public String login(String username, String password) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (Exception e){
            throw new UnauthorizedException("用户名或密码错误！");
        }
        User user = (User) authentication.getPrincipal();
        //user.getUsername();
       String token = tokenUtils.createToken(user);
       logger.info(token);

        return token;
    }
}
