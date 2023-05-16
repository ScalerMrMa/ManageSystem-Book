package com.it.domain;

import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Data
public class User {

    private String email;       // 邮箱
    private String username;    // 用户名
    private String password;    // 密码
    private String  identity;   // 身份
}
