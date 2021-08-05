package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.User;

public interface UserMapper {

    User selectUserByUserName(String username);

    Boolean checkUsernameUnique(String username);

    Integer insertUser(User user);
}
