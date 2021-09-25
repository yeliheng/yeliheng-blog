package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogsystem.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

    @Autowired
    private IUserService userService;

    public Long getLoginUserId(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return userService.selectUidByUsername(username);

    }
}
