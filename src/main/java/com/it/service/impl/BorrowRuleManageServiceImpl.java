package com.it.service.impl;

import com.it.dao.BorrowRuleManageDao;
import com.it.domain.BorrowRuleManage;
import com.it.service.BorrowRuleManageService;
import com.it.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Service
public class BorrowRuleManageServiceImpl implements BorrowRuleManageService {
    @Autowired
    private BorrowRuleManageDao borrowRuleManageDao;
    @Override
    public DataVo<BorrowRuleManage> getBorrowRuleManage() {
        // 数据模型
        DataVo<BorrowRuleManage> borrowRuleManageVo = new DataVo();
        borrowRuleManageVo.setCode(0);      // 状态码
        borrowRuleManageVo.setMsg("");

        // 总记录数
        Integer count = borrowRuleManageDao.selectCount(null);
        borrowRuleManageVo.setCount(count);

        // 查询规则
        List<BorrowRuleManage> borrowRuleManages = borrowRuleManageDao.selectList(null);
        borrowRuleManageVo.setData(borrowRuleManages);

        return borrowRuleManageVo;
    }
}
