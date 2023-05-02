package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.dao.BorrowRuleManageDao;
import com.it.domain.BorrowRuleManage;
import com.it.service.BorrowRuleManageService;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
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

    // 添加借阅规则
    @Override
    public ResultVo addBorrowRuleManage(BorrowRuleManage borrowRuleManage) {
        // 进行查询
        int insert = borrowRuleManageDao.insert(borrowRuleManage);

        // 根据插入结果向前端返回响应结果
        ResultVo resultVo = new ResultVo();
        if (insert != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("添加成功！");
            return resultVo;
        }else {
            resultVo.setMsg("添加失败！");
            resultVo.setCode(1);
            return resultVo;
        }
    }

    // 批量删除借阅规则
    public ResultVo deleteAllBorrowManage(List<Integer> numbers) {
        // 创建链式条件构造器
        LambdaQueryWrapper<BorrowRuleManage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BorrowRuleManage::getNumber, numbers);

        int delete = borrowRuleManageDao.delete(lambdaQueryWrapper);
        // 创建返回信息对象
        ResultVo resultVo = new ResultVo();
        if (delete != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败");
            return resultVo;
        }
    }

    // 删除单条借阅
    public ResultVo deleteBorrowRule(Integer number) {
        // 创建条件构造器
        QueryWrapper<BorrowRuleManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);

        int delete = borrowRuleManageDao.delete(queryWrapper);
        // 创建返回信息对象
        ResultVo resultVo = new ResultVo();

        if (delete != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败！");
            return resultVo;
        }
    }

    // 修改表单数据
    public ResultVo borrowRuleManage(BorrowRuleManage borrowRuleManage) {
        QueryWrapper<BorrowRuleManage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("number", borrowRuleManage.getNumber());

        int update = borrowRuleManageDao.update(borrowRuleManage, queryWrapper);

        ResultVo resultVo = new ResultVo();
        if (update != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("数据已经修改！");
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("数据修改失败！");
        }
        return resultVo;
    }
}
