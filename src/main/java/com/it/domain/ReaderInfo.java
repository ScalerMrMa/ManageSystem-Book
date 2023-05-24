package com.it.domain;

import lombok.Data;

import java.util.Date;

/**
 * 读者信息
 * @author MrMa
 * @version 1.0
 * @description 读者信息
 */
@Data
public class ReaderInfo {
    private String username;
    private String readerName;          // 读者姓名
    private String readerId;            // 读者Id
    private String gender;              // 性别
    private Date birthday;              // 生日
    private Date registrationDate;      // 登记日期

}
