package com.it.domain;

import lombok.Data;

import java.util.Date;

/**
 * 读者类别
 * @author MrMa
 * @version 1.0
 * @description 读者类别，类别不同可以借阅的书籍数量也不同
 */
@Data
public class ReaderCategory {

    private String category;        // 读者类别
    private int borrowingPeriod;   // 借阅期限
    private Date expirationDate;    // 到期时间
    private int borrowedBooksLimit;      // 能够借阅的书籍数量上线
}
