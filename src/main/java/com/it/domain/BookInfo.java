package com.it.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 图书信息
 * @author MrMa
 * @version 1.0
 * @description 图书的信息，包括书名，作者，书号等
 */

@Data

public class BookInfo {

    @TableField(value = "ISBN")
    private String ISBN;            // 书号
    private String bookName;        // 书名
    private String bookCategory;    // 图书类别
    private String author;          // 作者
    private String publisher;       // 出版社
    private String bookInformation; // 图书信息
    private Date publicationDate;   // 出版时间
    private Date registrationDate;  // 登记时间
}
