package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.domain.User;
import org.springframework.stereotype.Service;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Service
//@Transactional(rollbackFor = RuntimeException.class)
public interface LogService extends IService<User> {

    // 注册用户
    public int insertUser(User user);

    // 根据email获取用户名
    public String getUserNameByEmail(String email);
}
