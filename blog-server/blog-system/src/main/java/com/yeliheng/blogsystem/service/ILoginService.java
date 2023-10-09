package com.yeliheng.blogsystem.service;

import java.util.Map;

public interface ILoginService {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param rememberMe 记住我
     * @return token
     */
    public Map<String,String> login(String username, String password, boolean rememberMe);

    /**
     * 刷新Token
     * @return token
     */
    public String refreshToken(String token);

}
