package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dao.UserDao;
import com.it.domain.User;
import com.it.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Service
public class LogServiceImpl extends ServiceImpl<UserDao, User> implements LogService {

    @Autowired
    UserDao userDao;


    /**
     *
     * @param user
     * @return int 表示插入成功还是失败
     * @description 对注册的用户插入到数据库中
     */
    @Override
    public int insertUser(User user) {
       return userDao.insert(user);
    }

    @Override
    public String getUserNameByEmail(String email) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, email);

        User user = new User();
        try {
            user = userDao.selectOne(lambdaQueryWrapper);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return user.getUsername();
    }
}
