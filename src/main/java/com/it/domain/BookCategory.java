package com.it.domain;

import lombok.Data;

/**
 *  图书类别
 * @author MrMa
 * @version 1.0
 * @description 图书的分类
 */


@Data
public class BookCategory {
    private String bookCategory;    // 图书类别
    private String ISBN;            // 图书国际书号
}
