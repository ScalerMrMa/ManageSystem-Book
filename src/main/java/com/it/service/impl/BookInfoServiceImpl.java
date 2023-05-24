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
    private BookInfoDao bookInfoDao;
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
            BookInfoList = bookInfoDao.selectList(lambdaQueryWrapper);
            count = Math.toIntExact(bookInfoDao.selectCount(lambdaQueryWrapper));
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

            int insert = bookInfoDao.insert(bookInfo);
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
        List<BookInfo> BookInfoList =bookInfoDao.selectList(BookInfoLambdaQueryWrapper);
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
            int updateCount = bookInfoDao.update(null, lambdaUpdateWrapper);
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
        BookInfo bookInfo = bookInfoDao.selectOne(lambdaUpdateWrapper);

        // 构造操作结果
        ResultVo resultVo = new ResultVo();
        // 更新字段
        try {
            if (bookInfo.getBookStatus().equals("下架")) {
                resultVo.setCode(1);
                resultVo.setMsg("当前图书已下架！");
            }else {
                lambdaUpdateWrapper.set(BookInfo::getBookStatus, "下架");
                int update = bookInfoDao.update(bookInfo, lambdaUpdateWrapper);
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
        BookInfo bookInfo = bookInfoDao.selectOne(lambdaUpdateWrapper);

        // 构造操作结果
        ResultVo resultVo = new ResultVo();
        // 更新字段
        try {
            if (bookInfo.getBookStatus().equals("上架")) {
                resultVo.setCode(1);
                resultVo.setMsg("当前图书已上架！");
            }else{
                lambdaUpdateWrapper.set(BookInfo::getBookStatus, "上架");
                int update = bookInfoDao.update(null, lambdaUpdateWrapper);
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
        BookInfo baseBookInfo = bookInfoDao.selectOne(lambdaUpdateWrapper);
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
            int update = bookInfoDao.update(bookInfo, lambdaUpdateWrapper);
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
        Integer bookInfoCount = Math.toIntExact(bookInfoDao.selectCount(null));
        return bookInfoCount;
    }

    /**
     * 根据关键字搜索书籍信息
     * @return
     */
    @Override
    public DataVo<BookInfo> getSearchBookInfo(String keyword) {
        // 创建条件构造器
        LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 为了简单，就只是进行图书名称模糊查询
        lambdaQueryWrapper.like(BookInfo::getBookName, keyword);

        // 创建返回结果类
        DataVo<BookInfo> dataVo = new DataVo<>();
        // 创建列表接收查询结果
        List<BookInfo> bookInfos = null;
        try {
            // 根据keyword进行一次判断,如果是空字符串，就不查询了
            if (keyword.equals("")) {
                return dataVo;
            }

            bookInfos = bookInfoDao.selectList(lambdaQueryWrapper);
            if (bookInfos.size() == 0) {
                dataVo.setCode(1);  // 状态码,此状态码表示没有查找到相关内容
                dataVo.setMsg("抱歉！没有找到您想要的信息！");
                dataVo.setCount(0);
                dataVo.setData(bookInfos);
            }else {
                dataVo.setCode(0);  // 状态码,此状态码表示查询成功
                dataVo.setMsg("查询成功！");
                dataVo.setCount(bookInfos.size());
                dataVo.setData(bookInfos);
            }
        }catch (Exception e) {
            e.printStackTrace();
            dataVo.setCode(2);  // 状态码,此状态码表示异常
            dataVo.setMsg("糟糕！错误了！");
            dataVo.setCount(0);
        }
        return dataVo;
    }

    /**
     * 根据参数，查询对应数据
     * @param requestData
     * @return
     */
    @Override
    public DataVo<BookInfo> getCategoriesBook(String requestData) {
        // 创建条件构造器
        LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //  创建结果返回集
        DataVo<BookInfo> bookInfoDataVo = new DataVo<>();
        try {
            if (requestData.equals("全部")) {
                // 查询条目数
                Integer count = Math.toIntExact(bookInfoDao.selectCount(lambdaQueryWrapper));
                // 查询结果
                List<BookInfo> bookInfos = bookInfoDao.selectList(lambdaQueryWrapper);
                bookInfoDataVo.setCode(0);
                bookInfoDataVo.setMsg("查询成功！");
                bookInfoDataVo.setCount(count);
                bookInfoDataVo.setData(bookInfos);
            }else {
                lambdaQueryWrapper.like(BookInfo::getBookCategory, requestData);
                Integer count = Math.toIntExact(bookInfoDao.selectCount(lambdaQueryWrapper));
                // 查询结果
                List<BookInfo> bookInfos = bookInfoDao.selectList(lambdaQueryWrapper);
                bookInfoDataVo.setCode(0);
                bookInfoDataVo.setMsg("查询成功！");
                bookInfoDataVo.setCount(count);
                bookInfoDataVo.setData(bookInfos);
            }

        }catch (Exception e) {
            e.printStackTrace();
            bookInfoDataVo.setCode(3);
            bookInfoDataVo.setMsg("查询失败！");
            bookInfoDataVo.setCount(0);
            bookInfoDataVo.setData(null);
        }
        return bookInfoDataVo;
    }


}
