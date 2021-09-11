package com.yeliheng.blogsystem.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;

public interface ILoginService {
    public String login(String username,String password);

}
