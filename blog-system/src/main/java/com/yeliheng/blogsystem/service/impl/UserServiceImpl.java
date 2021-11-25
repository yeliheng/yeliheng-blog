package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.entity.User;
import com.yeliheng.blogsystem.entity.UserRole;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.mapper.UserRoleMapper;
import com.yeliheng.blogsystem.service.IUserService;
import com.yeliheng.blogsystem.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    //生成强哈希密码
    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByUserName(String username) {
        return userMapper.selectUserByUserName(username);
    }

    /**
     *
     * 检查用户名是否唯一
     * @param username 用户名
     * @return true/false
     */
    public Boolean checkUsernameUnique(String username) {
        Long userId = userMapper.checkUsernameUnique(username);
        return StringUtils.isNull(userId);
    }

    /**
     *
     * 插入用户(管理员手动添加)
     * @param user 用户实体
     */
    @Override
    @Transactional
    public void insertUser(User user) {
        if(!checkUsernameUnique(user.getUsername())) throw new GeneralException("用户已存在!");
        user.setPassword(encryptPassword(user.getPassword()));
        Integer rows = userMapper.insertUser(user);
        if(rows <= 0) throw new InternalServerException("插入失败，未知错误");
        //添加角色关联
        insertUserRole(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户实体
     */
    @Override
    @Transactional
    public void updateUser(User user) {
        if(StringUtils.isNotNull(user.getPassword()))
            user.setPassword(encryptPassword(user.getPassword()));
        int rows = userMapper.updateUser(user);
        if(rows <= 0) throw new InternalServerException("更新失败，未知错误");
        //删除角色关联
        deleteUserRole(user);
        //添加角色关联
        insertUserRole(user);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = new User();
        user.setId(userId);
        checkUserCanBeDel(user);
        deleteUserRole(user);
        if(userMapper.deleteByPrimaryKey(userId) <= 0) {
            throw new GeneralException("删除失败，用户可能不存在");
        }
    }

    /**
     *
     * 用户注册
     * @param user 用户实体
     * @return true/false
     */
    @Override
    public Boolean registerUser(User user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     *
     * 通过用户名查询用户id
     * @param username 用户名
     * @return 用户id
     */
    @Override
    public Long selectUidByUsername(String username) {
       return userMapper.selectUidByUsername(username);
    }

    /**
     * 获取用户列表
     *
     * @param page     第几页
     * @param pageSize 一页多少
     * @return 用户列表
     */
    @Override
    public PageInfo<User> getUserList(Integer page, Integer pageSize,User user) {
        PageHelper.startPage(page,pageSize);
        List<User> userList = userMapper.selectUserList(user);
        return new PageInfo<>(userList);
    }


    /**
     *
     * 插入用户角色关联
     * @param user 用户实体
     */
    public void insertUserRole(User user){
        Long[] roles = user.getRoles();
        if(StringUtils.isNotNull(roles)){
            //新增角色关联
            List<UserRole> list = new ArrayList<>();
            for(Long roleId : roles){
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                list.add(userRole);
            }
            if(list.size() > 0)
                userRoleMapper.batchUserRole(list);
        }
    }

    public void deleteUserRole(User user) {
        userRoleMapper.deleteByUserId(user.getId());
    }

    /**
     * 检查用户是否可被删除
     * @param user 用户实体
     */
    public void checkUserCanBeDel(User user){
        if(StringUtils.isNotNull(user.getId()) && user.isAdmin()){
            throw new GeneralException("不允许操作超级管理员!");
        }
    }

}
