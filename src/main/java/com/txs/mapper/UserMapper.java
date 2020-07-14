package com.txs.mapper;

import com.txs.bean.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface UserMapper {

    public User selectUser(Map<String,Object> map);
}
