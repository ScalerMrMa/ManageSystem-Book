package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.domain.User;
import com.it.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */

@Controller
@RequestMapping("/")
public class LogController {

    @Autowired
    LogService logService;

    @Autowired
    HttpSession httpSession;

    // 事务管理器
    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @PostMapping("login")
    public String checkLogIn(@RequestParam String email, @RequestParam String password) {
        // 将获取的参数封装成类
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        // 构造查询条件
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("email", user.getEmail());

        // 根据设置的条件查询出User对象
        User checkUser = logService.getOne(userQueryWrapper);

        // 进行判断，如果用户名和密码正确，则将用户信息存入session，并跳转到主页
        if (checkUser != null && checkUser.getPassword().equals(user.getPassword())) {
            if (checkUser.getIdentity().equals("管理员")) {
                httpSession.setAttribute("email", email);
                return "adminHomePage";
            }else if (checkUser.getIdentity().equals("图书管理员")) {
                httpSession.setAttribute("email", email);
                return "librarian";
            }else {
                httpSession.setAttribute("email", email);
                return "userHomePage";
            }
        } else {
            // 如果用户名或密码错误，则返回登录页面并显示错误信息
            ModelAndView modelAndView = new ModelAndView();

            return "错误";
        }
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
