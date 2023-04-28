package com.it.domain;
import lombok.Data;

/**
 * 借阅信息
 * @author MrMa
 * @version 1.0
 * @description 图书的借阅信息
 */
@Data
public class BorrowingInformation {
    private String readerId;        // 读者的身份Id
    private String readerName;      // 读者的名字
    private String isbn;            // 书号
    private String bookName;        // 书名
    private String loanDate;        // 借阅时间
    private String returnDate;      // 归还日期
}
