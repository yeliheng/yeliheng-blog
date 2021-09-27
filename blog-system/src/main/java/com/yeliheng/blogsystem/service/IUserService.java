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

    /**
     *
     * 插入用户(管理员手动添加)
     * @param user 用户实体
     */
    public void insertUser(User user);

    /**
     *
     * 更新用户
     * @param user
     */
    public void updateUser(User user);

    /**
     *
     * 用户注册
     * @param user 用户实体
     * @return true/false
     */
    public Boolean registerUser(User user);

    /**
     *
     * 通过用户名查询用户id
     * @param username 用户名
     * @return 用户id
     */
    public Long selectUidByUsername(String username);

}
