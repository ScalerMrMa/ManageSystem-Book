package com.it.service.impl;

import com.it.dao.BookInfoDao;
import com.it.domain.BookInfo;
import com.it.service.BookInfoService;
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
public class BookInfoServiceImpl implements BookInfoService {

    @Autowired
    private BookInfoDao bookInfoDao;

    public DataVo<BookInfo> getBookInfoList() {
        // 创造查询条件
        DataVo<BookInfo> bookInfoDataVo = new DataVo<>();
        bookInfoDataVo.setCode(0);
        bookInfoDataVo.setMsg("");
        Integer count = bookInfoDao.selectCount(null);
        bookInfoDataVo.setCount(count);

        // 查询所有的数据
        List<BookInfo> bookInfoList = bookInfoDao.selectList(null);
        bookInfoDataVo.setData(bookInfoList);
        return bookInfoDataVo;
    }
}
