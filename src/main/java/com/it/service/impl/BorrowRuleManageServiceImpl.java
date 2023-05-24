package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.it.dao.BorrowRuleManageDao;
import com.it.domain.BorrowRuleManage;
import com.it.service.BorrowRuleManageService;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        Integer count = Math.toIntExact(borrowRuleManageDao.selectCount(null));
        borrowRuleManageVo.setCount(count);

        // 查询规则
        List<BorrowRuleManage> borrowRuleManages = borrowRuleManageDao.selectList(null);
        borrowRuleManageVo.setData(borrowRuleManages);

        return borrowRuleManageVo;
    }

    // 添加借阅规则
    @Override
    public ResultVo addBorrowRuleManage(BorrowRuleManage borrowRuleManage) {
        // 获取当前的时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);


        // 获取科室信息
        // 创造返回结果类
        ResultVo resultVo = new ResultVo();
        try {
            // 公告发布的时间
            borrowRuleManage.setPublishDate(format);
            // 公告更新时间
            borrowRuleManage.setEditDate(format);
            borrowRuleManage.setPublishStatus("正常");
            // 插入数据
            int insert = borrowRuleManageDao.insert(borrowRuleManage);
            if (insert != 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }
        return resultVo;
    }

    // 批量删除借阅规则
    public ResultVo forbidManyBorrowRules(List<Integer> numbers) {
        // 创建条件构造器
        LambdaUpdateWrapper<BorrowRuleManage> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        // 获取对象
        lambdaUpdateWrapper.in(BorrowRuleManage::getNumber, numbers);
        List<BorrowRuleManage> notions = borrowRuleManageDao.selectList(lambdaUpdateWrapper);

        lambdaUpdateWrapper.in(BorrowRuleManage::getNumber, numbers)
                .set(BorrowRuleManage::getPublishStatus, "失效");

        // 创建结果集
        ResultVo resultVo = new ResultVo();
        try {
            for (BorrowRuleManage notion : notions) {
                if (notion.getPublishStatus().equals("失效")) {
                    resultVo.setCode(1);
                    resultVo.setMsg("部分公告已失效！");
                    return resultVo;
                }
            }
            int updateCount = borrowRuleManageDao.update(null, lambdaUpdateWrapper);
            if (updateCount > 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }
        return resultVo;
    }

    // 禁用单条借阅
    public ResultVo forbidBorrowStatus(Integer number) {
        // 条件构造器
        LambdaUpdateWrapper<BorrowRuleManage> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(BorrowRuleManage::getNumber, number);
        // 根据id查询出公告
        BorrowRuleManage borrowRuleManage = borrowRuleManageDao.selectOne(lambdaUpdateWrapper);
        lambdaUpdateWrapper.set(BorrowRuleManage::getPublishStatus, "失效");

        // 构造操作结果
        ResultVo resultVo = new ResultVo();
        // 更新字段
        try {
            if (borrowRuleManage.getPublishStatus().equals("失效")) {
                resultVo.setCode(1);
                resultVo.setMsg("该公告已失效！");
                return resultVo;
            }
            int update = borrowRuleManageDao.update(borrowRuleManage, lambdaUpdateWrapper);
            if (update != 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }

        return resultVo;
    }

    // 修改表单数据
    public ResultVo borrowRuleManage(BorrowRuleManage borrowRuleManage) {
        // 根据notion的Id从数据库找到原来的公告发布的时间
        LambdaQueryWrapper<BorrowRuleManage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BorrowRuleManage::getNumber, borrowRuleManage.getNumber());
        BorrowRuleManage baseBorrowRule = borrowRuleManageDao.selectOne(lambdaQueryWrapper);

        // 获取当前的时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);

        // 创建返回结果
        ResultVo resultVo = new ResultVo();
        try {
            if (baseBorrowRule.getPublishStatus().equals("失效")) {
                resultVo.setCode(1);
                resultVo.setMsg("该公告已失效无法修改！");
                return resultVo;
            }
            // 设置创建时间
            borrowRuleManage.setPublishDate(baseBorrowRule.getPublishDate());
            // 设置更新时间
            borrowRuleManage.setEditDate(format);

            int update = borrowRuleManageDao.update(borrowRuleManage, lambdaQueryWrapper);
            if (update > 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功");
            }else {
                resultVo.setCode(1);
                resultVo.setMsg("并没有发生变化");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }

        return resultVo;
    }

    /**
     * 获取管理规则
     * @return
     */
    @Override
    public DataVo<BorrowRuleManage> getRules() {
        LambdaQueryWrapper<BorrowRuleManage> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        DataVo<BorrowRuleManage> dataVo = new DataVo<>();
        try {
            List<BorrowRuleManage> borrowRuleManages = borrowRuleManageDao.selectList(null);
            dataVo.setCode(0);
            dataVo.setMsg("查询成功！");
            dataVo.setCount(borrowRuleManages.size());
            dataVo.setData(borrowRuleManages);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dataVo;
    }
}
