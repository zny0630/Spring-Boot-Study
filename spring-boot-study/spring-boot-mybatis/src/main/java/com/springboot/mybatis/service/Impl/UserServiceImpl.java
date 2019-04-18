package com.springboot.mybatis.service.impl;

import com.springboot.mybatis.entity.User;
import com.springboot.mybatis.mapper.UserMapper;
import com.springboot.mybatis.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(long userId) {
        return userMapper.getOne(userId);
    }

    @Override
    public void delete(long userId) {
        userMapper.delete(userId);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}