package com.it.service.impl;

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
    public DataVo<BorrowingInformation> getBorrowInfo() {
        // 创建DataVo数据模型
        DataVo<BorrowingInformation> borrowingInformationDataVo = new DataVo<>();
        borrowingInformationDataVo.setCode(0);  // 设置状态码
        borrowingInformationDataVo.setMsg(""); // 设置信息

        // 记录数
        Integer count = borrowingInformationDao.selectCount(null);
        borrowingInformationDataVo.setCount(count);

        // 获取借阅信息
        List<BorrowingInformation> borrowingInformationList = borrowingInformationDao.selectList(null);
        borrowingInformationDataVo.setData(borrowingInformationList);

        return borrowingInformationDataVo;
    }
}
