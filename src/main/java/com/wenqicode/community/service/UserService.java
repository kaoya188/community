package com.wenqicode.community.service;

import com.wenqicode.community.mapper.UserMapper;
import com.wenqicode.community.model.User;
import com.wenqicode.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wenqi Liang
 * @date 2020/10/3
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
       if (users.size() == 0) {
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           User dbUser = users.get(0);
           User updateUser = new User();
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setName(user.getName());
           updateUser.setToken(user.getToken());

           UserExample example = new UserExample();
           example.createCriteria()
                   .andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser, example);
       }

    }
}
