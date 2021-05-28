package com.uncle.csdn.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.uncle.csdn.dao.UserMapper;
import com.uncle.csdn.entity.User;
import com.uncle.csdn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String name, String password) {

        if (StrUtil.isEmpty(name) || StrUtil.isEmpty(password)) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("password", DigestUtil.md5Hex(password));
        User user = userMapper.findUserByCondition(map);

        return user;
    }
}
