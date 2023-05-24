package com.it.domain;

import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Data
public class WorkUser {
   private String workUserId;          // 工作员工ID
   private String username;        // 用户名
   private String workUserRealName;    // 真实姓名
   private String workUserSex;         // 性别
   private String workUserAddress;     // 家庭地址
   private Integer workUserAge;         // 年龄
   private String workUserWorkStatus;  // 工作状态
   private String workUserAccountStatus; // 账户状态
   private String workUserPassword;    // 密码
   private String workUserIdentity;    // 身份
   private String workUserJobTime;     // 入职时间
   private String workUserResignTime;  // 离职时间
}
