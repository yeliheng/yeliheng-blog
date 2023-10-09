package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.InternalServerException;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.ExcelUtils;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.domain.User;
import com.yeliheng.blogsystem.domain.UserRole;
import com.yeliheng.blogsystem.mapper.UserMapper;
import com.yeliheng.blogsystem.mapper.UserRoleMapper;
import com.yeliheng.blogsystem.service.IUserService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.jeecgframework.poi.excel.entity.ExportParams;
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
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ExcelUtils excelUtils;


    //生成强哈希密码
    private String encryptPassword(String password){
        if(StringUtils.isNull(password))
            throw new RequestFormatException("密码不能为空");
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
        return userMapper.checkUsernameUnique(username) <= 0;
    }

    /**
     *
     * 检查电子邮箱是否唯一
     * @param user 用户实体
     * @return true/false
     */
    public Boolean checkEmailUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        //判断是不是该用户
        if(StringUtils.isNotNull(info) && info.getId().equals(userId))
            return true;
        else return StringUtils.isNull(info);
    }

    /**
     *
     * 检查电话是否唯一
     * @param user 用户实体
     * @return true/false
     */
    public Boolean checkPhoneUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkPhoneUnique(user.getPhone());
        //判断是不是该用户
        if(StringUtils.isNotNull(info) && info.getId().equals(userId))
            return true;
        else return StringUtils.isNull(info);
    }

    /**
     *
     * 插入用户(管理员手动添加)
     * @param user 用户实体
     */
    @Override
    @Transactional
    public void insertUser(User user) {
        checkUserCanBeOperate(user);
        if(!checkUsernameUnique(user.getUsername())) throw new GeneralException("添加失败,用户已存在!");
        User info1 = userMapper.checkEmailUnique(user.getEmail());
        User info2 = userMapper.checkPhoneUnique(user.getPhone());
        if(StringUtils.isNotNull(info1)) throw new GeneralException("添加失败,邮箱已存在!");
        if(StringUtils.isNotNull(info2)) throw new GeneralException("添加失败,手机号已存在!");
        user.setPassword(encryptPassword(user.getPassword()));
        int rows = userMapper.insertUser(user);
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
        checkUserAllowed(user);
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
     * 更新时检查用户名是否唯一
     */
    public Boolean checkUpdateUsernameUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkUpdateUsernameUnique(user.getUsername());
        //判断是不是该用户
        if(StringUtils.isNotNull(info) && info.getId().equals(userId))
            return true;
        else return StringUtils.isNull(info);
    }
    public void checkUserAllowed(User user) {
        checkUserCanBeOperate(user);
        if(StringUtils.isNotEmpty(user.getUsername()) && !checkUpdateUsernameUnique(user))
            throw new GeneralException("修改失败,用户名已存在!");
        if(StringUtils.isNotEmpty(user.getEmail()) && !checkEmailUnique(user))
            throw new GeneralException("修改失败,邮箱已存在!");
        if(StringUtils.isNotEmpty(user.getPhone()) && !checkPhoneUnique(user))
            throw new GeneralException("修改失败,手机号已存在!");
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
        checkUserCanBeOperate(user);
        deleteUserRole(user);
        if(userMapper.deleteUserById(userId) <= 0) {
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
     * 修改个人信息
     *
     * @param user 用户实体
     */
    @Override
    public void updateProfile(User user) {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        User setUser = loginUser.getUser();
        //以下三个属性不允许修改
        user.setId(setUser.getId());
        user.setUsername(setUser.getUsername());
        user.setPassword(setUser.getPassword());
        if(StringUtils.isNotEmpty(user.getEmail()) && !checkEmailUnique(user))
            throw new GeneralException("修改失败,邮箱已存在!");
        if(StringUtils.isNotEmpty(user.getPhone()) && !checkPhoneUnique(user))
            throw new GeneralException("修改失败,手机号已存在!");
        if(userMapper.updateUser(user) <= 0)
            throw new UnexpectedException();
        //刷新缓存
        loginUser.setUser(userMapper.selectUserByUserId(loginUser.getUser().getId()));
        tokenUtils.refreshLoginUser(loginUser);
    }

    /**
     * 修改个人密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void resetPassword(String oldPassword, String newPassword) {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(oldPassword,loginUser.getPassword()))
            throw new GeneralException("旧密码错误!");
        String newEncryptedPassword = encryptPassword(newPassword);
        User user = new User();
        user.setId(loginUser.getUser().getId());
        user.setPassword(newEncryptedPassword);
        if(userMapper.updateUser(user) <= 0)
            throw new UnexpectedException();
        //刷新缓存
        loginUser.setUser(userMapper.selectUserByUserId(loginUser.getUser().getId()));
        tokenUtils.refreshLoginUser(loginUser);
    }

    @Override
    public String exportUser(User user) {
        List<User> userList =  userMapper.selectUserList(user);
        ExportParams exportParams = new ExportParams();
        exportParams.setTitle("用户列表");
        exportParams.setSheetName("用户列表");
        return excelUtils.exportExcel(exportParams,userList,User.class);
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

    /**
     * 删除用户角色关联
     * @param user 用户实体
     */
    public void deleteUserRole(User user) {
        userRoleMapper.deleteByUserId(user.getId());
    }

    /**
     * 检查用户是否可操作
     * @param user 用户实体
     */
    public void checkUserCanBeOperate(User user){
        if(StringUtils.isNotNull(user.getId()) && user.isAdmin()){
            throw new GeneralException("不允许操作超级管理员!");
        }
    }

}
