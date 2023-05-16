package com.it.domain;

import lombok.Data;

/**
 * 图书信息
 * @author MrMa
 * @version 1.0
 * @description 图书的信息，包括书名，作者，书号等
 */

@Data
public class BookInfo {
    private String isbn;            // 书号
    private String bookName;        // 书名
    private String bookCategory;    // 图书类别
    private String author;          // 作者
    private String publisher;       // 出版社
    private String bookInformation; // 图书信息
    private String publicationDate;   // 出版时间
    private String registrationDate;  // 登记时间
    private String bookStatus;        // 图书状态
}
