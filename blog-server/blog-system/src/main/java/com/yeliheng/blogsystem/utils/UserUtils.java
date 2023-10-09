package com.yeliheng.blogsystem.utils;

import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private IUserService userService;
    @Autowired
    private TokenUtils tokenUtils;

    public Long getLoginUserId(){
        return tokenUtils.getLoginUser(ServletUtils.getRequest()).getUser().getId();
    }
}
