package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;

public interface IRegisterService {
    public Boolean register(RegisterUser registerUser);
}
