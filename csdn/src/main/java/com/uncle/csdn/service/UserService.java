package com.uncle.csdn.service;

import com.uncle.csdn.entity.User;

public interface UserService {


    User login(String name, String password);
}
