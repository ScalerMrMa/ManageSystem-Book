package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.domain.User;
import com.it.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */

@RestController
@RequestMapping("/")
public class LogController {

    @Autowired
    LogService logService;

    // 事务管理器
    @Resource
    private PlatformTransactionManager platformTransactionManager;
    /**
     * 判断用户是否存在
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public String checkLogIn(User user) {
        // 构造查询条件
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("email",user.getEmail());

        // 根据设置的条件查询出User对象
        User checkUser = logService.getOne(userQueryWrapper);
        System.out.println(checkUser);
        // 进行判断
        if (checkUser.getEmail().equals(user.getEmail()) &&
                checkUser.getPassword().equals(user.getPassword())) {
            return "登录成功";
        }
        return "登录失败";
    }


    @PostMapping("register")
    @ResponseBody
//    @Transactional
    public String register(User user) {
        try {
            int result = logService.insertUser(user);

            // 如果返回的数据为1，则插入成功
            if (result == 1) {
                return "注册成功";
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
        }

        return "用户已经存在";
    }
}
