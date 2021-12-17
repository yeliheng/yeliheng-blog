package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.User;

import java.util.List;

public interface UserMapper {

    User selectUserByUserName(String username);

    Long checkUsernameUnique(String username);

    int insertUser(User user);

    int updateUser(User user);

    Long selectUidByUsername(String username);

    List<User> selectUserList(User user);

    int deleteUserById(Long id);
}
