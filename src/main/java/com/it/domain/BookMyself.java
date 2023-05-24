package com.it.domain;

import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Data
public class BookMyself {

   private String email;    // 电子邮箱
   private String bookName;      // 图书信息
   private String author;        // 作者
   private String bookInformation;     // 图书信息
   private String borrow_way;          // 借阅途径，线上和线下两种
}
