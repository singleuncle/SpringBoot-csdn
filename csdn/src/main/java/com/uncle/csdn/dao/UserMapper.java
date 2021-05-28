package com.uncle.csdn.dao;

import com.uncle.csdn.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User findUserByCondition(Map<String, Object> map);

    List<User> findUserAll();

}
