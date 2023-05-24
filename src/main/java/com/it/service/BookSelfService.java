package com.it.service;

import com.it.domain.BookInfo;
import com.it.domain.BookMyself;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

import javax.servlet.http.HttpSession;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BookSelfService {

    // 添加到书架
    public ResultVo addToBookSelf(BookInfo bookInfo, HttpSession httpSession);

    public DataVo<BookMyself> getBookMyBooks(HttpSession httpSession);
}
