package com.it.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description 用来存放借阅图书的相关数据
 */
public class BorrowInfoDataVo {

    @Data
    public class DataVo<T> {
        private Integer code;       // 状态码
        private String msg;         // 提示信息
        private Integer count;      // 条目数
        private List<T> Data;       // 实体数据
        private Integer Days;       // 借阅天数
    }
}
