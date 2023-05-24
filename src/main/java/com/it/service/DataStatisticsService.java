package com.it.service;

import com.it.statistics.DataItem;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface DataStatisticsService {


    // 获取图书数量，出版社数量，作者数量，用户数量
    public List<Integer> dataCounts();

    // 图书信息差统计
    public List<DataItem> statistic();
}
