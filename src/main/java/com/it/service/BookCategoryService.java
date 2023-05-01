package com.it.service;

import com.it.domain.BookCategory;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BookCategoryService  {

    DataVo<BookCategory> getBookCategory();

    // 新增图书类别
    ResultVo insertBookCategory(BookCategory bookCategory);

    // 删除图书类别
    ResultVo deleteBookCategory(BookCategory bookCategory);

    // 批量删除图书类别
    ResultVo deleteAllBookCategory(List<String> isbns);

    // 修改图书类别
    public ResultVo updateBookCategory(BookCategory bookCategory);
}
