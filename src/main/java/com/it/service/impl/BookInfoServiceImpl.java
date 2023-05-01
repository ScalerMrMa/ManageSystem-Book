package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.dao.BookInfoDao;
import com.it.domain.BookCategory;
import com.it.domain.BookInfo;
import com.it.service.BookInfoService;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

    /**
     * 获取图书列表
     * @return
     */
    public DataVo<BookInfo> getBookInfoList() {
        // 创造查询条件
        DataVo<BookInfo> bookInfoDataVo = new DataVo<>();
        bookInfoDataVo.setCode(0);
        bookInfoDataVo.setMsg("");
        Integer count = bookInfoDao.selectCount(null);
        bookInfoDataVo.setCount(count);
        System.out.println(count);
        // 查询所有的数据
        List<BookInfo> bookInfoList = bookInfoDao.selectList(null);
        bookInfoDataVo.setData(bookInfoList);
        return bookInfoDataVo;
    }

    /**
     * 根据isbn删除图书信息
     */
    @Override
    public ResultVo deleteBookInfo(BookInfo bookInfo) {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", bookInfo.getIsbn());
        int delete = bookInfoDao.delete(queryWrapper);
        // 创建ResultVo

        ResultVo resultVo = new ResultVo();
        if (delete == 1) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败！");
            return resultVo;
        }
    }

    /**
     * 根据isbns批量删除图书信息
     * @param isbns
     * @return
     */
    @Override
    public ResultVo deleteAllBookInfo(List<String> isbns) {
        LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BookInfo::getIsbn, isbns);
        int delete = bookInfoDao.delete(lambdaQueryWrapper);
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

    /**
     * 新增图书信息
     * @param bookInfo
     */
    @Override
    public void insetBookInfo(BookInfo bookInfo) {
        bookInfoDao.insert(bookInfo);
    }



}
