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

    DataVo<BookInfo> getBookInfoList();

    // 删除图书信息
    ResultVo deleteBookInfo(BookInfo bookInfo);

    ResultVo deleteAllBookInfo(List<String> isbns);

    void insetBookInfo(BookInfo bookInfo);


}
