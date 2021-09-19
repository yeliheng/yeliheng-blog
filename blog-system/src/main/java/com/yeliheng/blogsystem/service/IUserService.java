package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.RegisterUser;
import com.yeliheng.blogsystem.entity.User;

public interface IUserService {
    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    public User selectUserByUserName(String username);

    public Boolean checkUsernameUnique(String username);

    public Integer insertUser(User user);

    public Boolean registerUser(User user);

    public Long selectUidByUsername(String username);



}
