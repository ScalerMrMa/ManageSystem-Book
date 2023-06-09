package com.it.domain;

import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description 公告内容实体类
 */
@Data
public class Announcement {
    private String publishNumber;  // 序号
    private String publishDate;     // 发布事件
    private String publisher;       // 发布者
    private String publishContent; // 发布内容
    private Integer publishDays;    // 发布时间
    private String publishStatus;    // 发布状态
    private String editDate;        // 修改时间
}
