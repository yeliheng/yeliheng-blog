package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    User selectUserByUserName(String username);

    Long checkUsernameUnique(String username);

    int insertUser(User user);

    int updateUser(User user);

    Long selectUidByUsername(String username);

    List<User> selectUserList(User user);

}
