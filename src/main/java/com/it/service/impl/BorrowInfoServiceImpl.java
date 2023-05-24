package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.dao.BorrowingInformationDao;
import com.it.domain.BorrowingInformation;
import com.it.service.BorrowInfoService;
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
public class BorrowInfoServiceImpl implements BorrowInfoService {

    @Autowired
    BorrowingInformationDao borrowingInformationDao;

    @Override
    public DataVo<BorrowingInformation> getBorrowInfo(String readerId, String bookName) {
        // 创建QueryWrapper条件构造器
        QueryWrapper<BorrowingInformation> queryWrapper = new QueryWrapper<>();
        // 对字段内容进行判断
        if (readerId == null && bookName == null) {
            // 创建DataVo数据模型
            DataVo<BorrowingInformation> borrowingInformationDataVo = new DataVo<>();
            borrowingInformationDataVo.setCode(0);  // 设置状态码
            borrowingInformationDataVo.setMsg(""); // 设置信息

            // 记录数
            Integer count = Math.toIntExact(borrowingInformationDao.selectCount(null));
            borrowingInformationDataVo.setCount(count);

            // 获取借阅信息
            List<BorrowingInformation> borrowingInformationList = borrowingInformationDao.selectList(null);
            borrowingInformationDataVo.setData(borrowingInformationList);

            return borrowingInformationDataVo;
        }
        if (readerId.length() > 0 && bookName.length() == 0) {
            queryWrapper.like("reader_id", readerId);
        }else if (readerId.length() == 0 && bookName.length() > 0) {
            queryWrapper.like("book_name", bookName);
        }else {
            queryWrapper.like("reader_id", readerId).or().like("book_name", bookName);
        }
        // 创建DataVo数据模型
        DataVo<BorrowingInformation> borrowingInformationDataVo = new DataVo<>();
        borrowingInformationDataVo.setCode(0);  // 设置状态码
        borrowingInformationDataVo.setMsg(""); // 设置信息

        // 记录数
        Integer count = Math.toIntExact(borrowingInformationDao.selectCount(queryWrapper));
        borrowingInformationDataVo.setCount(count);

        // 获取借阅信息
        List<BorrowingInformation> borrowingInformationList = borrowingInformationDao.selectList(queryWrapper);
        borrowingInformationDataVo.setData(borrowingInformationList);

        return borrowingInformationDataVo;
    }
}
