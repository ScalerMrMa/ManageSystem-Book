package com.it.service;

import com.it.domain.BorrowRuleManage;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BorrowRuleManageService {

    // 获取图书借阅规则
    DataVo<BorrowRuleManage> getBorrowRuleManage();

    // 添加图书借阅规则
    ResultVo addBorrowRuleManage(BorrowRuleManage borrowRuleManage);

    // 批量删除图书借阅规则
    ResultVo deleteAllBorrowManage(List<Integer> numbers);

    // 删除单挑图书借阅规则
    ResultVo deleteBorrowRule(Integer number);
}
