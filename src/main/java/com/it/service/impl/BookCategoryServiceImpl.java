package com.it.service.impl;

import com.it.dao.BookCategoryDao;
import com.it.domain.BookCategory;
import com.it.service.BookCategoryService;
import com.it.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryDao bookCategoryDao;

    /**
     * 查询所有的图书种类
     * @return
     */
    @Override
    public DataVo<BookCategory> getBookCategory() {
        // 创建Vo模型
        DataVo<BookCategory> bookCategoryVo = new DataVo<BookCategory>();
        bookCategoryVo.setCode(0);    // 设置状态码
        bookCategoryVo.setMsg("");

        // 查询记录数
        Integer count = bookCategoryDao.selectCount(null);
        bookCategoryVo.setCount(count);

        // 查询所有的分类
        List<BookCategory> bookCategories = bookCategoryDao.selectList(null);
        bookCategoryVo.setData(bookCategories);
        return bookCategoryVo;
    }
}
