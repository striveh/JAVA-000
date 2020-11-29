package com.example.demo.service;

import com.example.demo.annotation.DS;
import com.example.demo.constants.DataSourceConstants;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @DS(DataSourceConstants.DS_KEY_MASTER)
    public void addUser(User user){
        userMapper.insert(user);
    }

    @DS(DataSourceConstants.DS_KEY_SLAVE1)
    public List<User> getAll(){
        return userMapper.selectList(null);
    }



}
