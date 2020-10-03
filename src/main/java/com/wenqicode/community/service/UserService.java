package com.wenqicode.community.service;

import com.wenqicode.community.mapper.UserMapper;
import com.wenqicode.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wenqi Liang
 * @date 2020/10/3
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
       User dbUser = userMapper.findByAccountId(user.getAccountId().toString());
       if (dbUser == null) {
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);
       }

    }
}
