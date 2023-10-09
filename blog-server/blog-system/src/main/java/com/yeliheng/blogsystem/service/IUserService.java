package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.User;

import java.util.List;

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
     * 删除用户
     * @param userId 用户id
     */
    public void deleteUser(Long userId);

    /**
     *
     * 用户注册
     * @param user 用户实体
     * @return true/false
     */
    public Boolean registerUser(User user);

    /**
     *
     * 获取用户列表
     * @param page 第几页
     * @param pageSize 一页多少
     * @return 用户列表
     */
    public PageInfo<User> getUserList(Integer page, Integer pageSize,User user);

    /**
     * 修改个人信息
     * @param user 用户实体
     */
    public void updateProfile(User user);

    /**
     * 修改个人密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public void resetPassword(String oldPassword, String newPassword);

    public String exportUser(User user);

}
