package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.it.dao.BookInfoDao;
import com.it.domain.BookInfo;
import com.it.service.BookInfoService;
import com.it.util.SnowflakeIdGenerator;
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
public class BookInfoServiceImpl implements BookInfoService {
    @Autowired
    private BookInfoDao BookInfoDao;
    /**
     * 搜索、信息展示
     * @param bookName
     * @return
     */
    @Override
    public DataVo<BookInfo> getBookInfos(String bookName) {

        // 条件构造器
        LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 创建返回结果类
        DataVo<BookInfo> dataVo = new DataVo<>();
        Integer count = 0;
        // 图书信息集合
        List<BookInfo> BookInfoList = null;
        try {
            // 判断是否进行搜索
            if (bookName != null) {
                lambdaQueryWrapper.like(BookInfo::getBookName, bookName);
            }
            BookInfoList = BookInfoDao.selectList(lambdaQueryWrapper);
            count = BookInfoDao.selectCount(lambdaQueryWrapper);
            dataVo.setCode(0);
            dataVo.setMsg("查询成功");
            dataVo.setCount(count);
            dataVo.setData(BookInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            dataVo.setCode(-1);
            dataVo.setMsg("查询异常");
            dataVo.setCount(0);
            dataVo.setData(null);
        }

        return dataVo;
    }

    /**
     * 添加图书信息
     * @return
     */
    @Override
    public ResultVo addBookInfo(BookInfo bookInfo) {
        // 创造返回结果类
        // 雪花算法生成ID
        bookInfo.setIsbn(SnowflakeIdGenerator.generate());


        ResultVo resultVo = new ResultVo();
        try {
            // 生成一些基本信息,插入固定数据
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(date);
            bookInfo.setRegistrationDate(format);
            bookInfo.setBookStatus("上架");

            int insert = BookInfoDao.insert(bookInfo);
            if (insert != 0) {
                resultVo.setCode(0);
                resultVo.setMsg("添加成功！");
            }else {
                resultVo.setCode(3);
                resultVo.setMsg("操作失败！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }
        return resultVo;
    }

    /**
     * 批量禁用图书
     * @param isbns
     * @return
     */
    public ResultVo forbidBookInfos(List<String> isbns) {
        // 创建条件构造器
        System.out.println(isbns);
        LambdaUpdateWrapper<BookInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(BookInfo::getIsbn, isbns)
                .set(BookInfo::getBookStatus, "下架");
        LambdaQueryWrapper<BookInfo> BookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        BookInfoLambdaQueryWrapper.in(BookInfo::getIsbn, isbns);
        // 获取对象
        List<BookInfo> BookInfoList = BookInfoDao.selectList(BookInfoLambdaQueryWrapper);
        // 创建结果集
        ResultVo resultVo = new ResultVo();
        try {
            for (BookInfo BookInfo : BookInfoList) {
                if (BookInfo.getBookStatus().equals("下架")) {
                    resultVo.setCode(1);
                    resultVo.setMsg("部分书籍已经下架！此操作无效");
                    return resultVo;
                }
            }
            int updateCount = BookInfoDao.update(null, lambdaUpdateWrapper);
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

    /**
     * 禁用图书
     * @param isbn
     * @return
     */
    @Override
    public ResultVo forbidBookInfo(String isbn) {
        // 条件构造器
        LambdaUpdateWrapper<BookInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(BookInfo::getIsbn, isbn);
        // 根据id查询出图书
        BookInfo bookInfo = BookInfoDao.selectOne(lambdaUpdateWrapper);

        // 构造操作结果
        ResultVo resultVo = new ResultVo();
        // 更新字段
        try {
            if (bookInfo.getBookStatus().equals("下架")) {
                resultVo.setCode(1);
                resultVo.setMsg("当前图书已下架！");
            }else {
                lambdaUpdateWrapper.set(BookInfo::getBookStatus, "下架");
                int update = BookInfoDao.update(bookInfo, lambdaUpdateWrapper);
                if (update != 0) {
                    resultVo.setCode(0);
                    resultVo.setMsg("已下架！");
                }else {
                    resultVo.setCode(3);
                    resultVo.setMsg("操作失败！");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }

        return resultVo;
    }

    /**
     * 启用图书
     * @param isbn
     * @return
     */
    @Override
    public ResultVo activeBookInfo(String isbn) {
        // 条件构造器
        LambdaUpdateWrapper<BookInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(BookInfo::getIsbn, isbn);
        // 根据id查询出图书
        BookInfo bookInfo = BookInfoDao.selectOne(lambdaUpdateWrapper);

        // 构造操作结果
        ResultVo resultVo = new ResultVo();
        // 更新字段
        try {
            if (bookInfo.getBookStatus().equals("上架")) {
                resultVo.setCode(1);
                resultVo.setMsg("当前图书已上架！");
            }else{
                lambdaUpdateWrapper.set(BookInfo::getBookStatus, "上架");
                int update = BookInfoDao.update(null, lambdaUpdateWrapper);
                if (update != 0) {
                    resultVo.setCode(0);
                    resultVo.setMsg("已上架！");
                }else {
                    resultVo.setCode(2);
                    resultVo.setMsg("上架失败！");
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(2);
            resultVo.setMsg("启用失败！");
        }

        return resultVo;
    }

    /**
     * 修改图书
     * @param bookInfo
     * @return
     */
    @Override
    public ResultVo updateBookInfo(BookInfo bookInfo) {
        // 创造条件构造器
        LambdaUpdateWrapper<BookInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(BookInfo::getIsbn, bookInfo.getIsbn());
        // 通过Id获取图书的基础信息
        BookInfo baseBookInfo = BookInfoDao.selectOne(lambdaUpdateWrapper);
        bookInfo.setRegistrationDate(baseBookInfo.getRegistrationDate());
        bookInfo.setBookStatus(baseBookInfo.getBookStatus());

        // 创建返回结果类
        ResultVo resultVo = new ResultVo();
        try {
            System.out.println(baseBookInfo.getBookStatus().equals("禁用"));
            if (baseBookInfo.getBookStatus().equals("禁用")) {
                resultVo.setCode(1);
                resultVo.setMsg("该图书已经被禁用！无法修改！");
                return resultVo;
            }
            // 如果前端传来的BookInfo的密码为null，那么就从数据库中将原来的密码查询出来
            int update = BookInfoDao.update(bookInfo, lambdaUpdateWrapper);
            if (update > 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功！");
            }else {
                resultVo.setCode(3);
                resultVo.setMsg("操作失败！");
            }
        }catch (Exception e) {
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }

        return resultVo;
    }


    // 获取图书数目
    @Override
    public Integer getBookInfoCount() {
        Integer bookInfoCount = BookInfoDao.selectCount(null);
        return bookInfoCount;
    }
}
