package com.it.service;

import com.it.domain.BookCategory;
import com.it.vo.DataVo;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BookCategoryService  {

    DataVo<BookCategory> getBookCategory();
}
