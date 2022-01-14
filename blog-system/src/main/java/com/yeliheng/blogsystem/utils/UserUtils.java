package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogcommon.utils.ServletUtils;
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
    @Autowired
    private TokenUtils tokenUtils;

    public Long getLoginUserId(){
        return tokenUtils.getLoginUser(ServletUtils.getRequest()).getUser().getId();
    }
}
