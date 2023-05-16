package com.it.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */

@Data
public class DataVo<T> {
    private Integer code;       // 状态码
    private String msg;         // 提示信息
    private Integer count;      // 条目数
    private List<T> data;       // 实体数据

}
