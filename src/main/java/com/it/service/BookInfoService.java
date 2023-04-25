package com.it.service;

import com.it.domain.BookInfo;
import com.it.vo.DataVo;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BookInfoService{

    DataVo<BookInfo> getBookInfoList();
}
