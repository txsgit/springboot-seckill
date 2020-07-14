package com.txs.service.impl;

import com.txs.bean.User;
import com.txs.mapper.UserMapper;
import com.txs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfo(Map<String, Object> map) {
        return userMapper.selectUser(map);
    }
}
