package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.User;

import java.util.List;

public interface UserMapper {

    User selectUserByUserName(String username);

    int checkUsernameUnique(String username);

    User checkUpdateUsernameUnique(String username);

    User checkEmailUnique(String email);

    User checkPhoneUnique(String phone);

    User selectUserByUserId(Long id);

    int insertUser(User user);

    int updateUser(User user);

    Long selectUidByUsername(String username);

    List<User> selectUserList(User user);

    int deleteUserById(Long id);

}
