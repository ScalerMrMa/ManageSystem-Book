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
    DataVo<BookInfo> getBookInfoList(String bookName);

    // 删除图书信息
    ResultVo deleteBookInfo(BookInfo bookInfo);

    // 删除图书西信息
    ResultVo deleteAllBookInfo(List<String> isbns);

    // 新增图书信息
    ResultVo insetBookInfo(BookInfo bookInfo);

    // 编辑图书信息
    ResultVo updateBookInfo(BookInfo bookInfo);
}
