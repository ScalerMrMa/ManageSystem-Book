package com.it.service;

import com.it.domain.BookInfo;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BookInfoService{

    // 获取图书列表
    DataVo<BookInfo> getBookInfos(String bookName);

    // 删除图书信息
    ResultVo forbidBookInfo(String isbn);

    // 删除图书信息
    ResultVo forbidBookInfos(List<String> isbns);

    // 新增图书信息
    ResultVo addBookInfo(BookInfo bookInfo);

    // 编辑图书信息
    ResultVo updateBookInfo(BookInfo bookInfo);

    // 上架图书
    public ResultVo activeBookInfo(String isbn);

    // 获取图书条数
    public Integer getBookInfoCount();


    // 实现用户首页的搜索功能
    public DataVo<BookInfo> getSearchBookInfo(String keyword);

    public DataVo<BookInfo> getCategoriesBook(String requestData);
}
