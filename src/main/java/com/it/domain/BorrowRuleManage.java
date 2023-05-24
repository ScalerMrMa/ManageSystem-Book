package com.it.domain;

import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Data
public class BorrowRuleManage {
    private Long number;         // 序号
    private String publisher;       // 发布者
    private String borrowRules;     // 图书借阅规则
    private String publishDate;       // 发布日期
    private String editDate;        // 修改日期
    private String publishStatus;   // 状态
}
