package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.User;

public interface UserMapper {

    User selectUserByUserName(String username);

    Long checkUsernameUnique(String username);

    Integer insertUser(User user);

    int updateUser(User user);

    Long selectUidByUsername(String username);

}
