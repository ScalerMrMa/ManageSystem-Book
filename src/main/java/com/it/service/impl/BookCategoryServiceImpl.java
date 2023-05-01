package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.dao.BookCategoryDao;
import com.it.domain.BookCategory;
import com.it.service.BookCategoryService;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
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

    /**
     * 新增图书类别
     * @return
     */
    @Override
    public ResultVo insertBookCategory(BookCategory bookCategory) {
        int check = bookCategoryDao.insert(bookCategory);
        if (check == 0) {
            ResultVo resultVo = new ResultVo();
            resultVo.setCode(1);
            resultVo.setMsg("添加失败！");
            return resultVo;
        }else {
            ResultVo resultVo = new ResultVo();
            resultVo.setCode(0);
            resultVo.setMsg("添加成功！");
            return resultVo;
        }
    }

    /**
     * 删除图书类别
     * @param bookCategory
     * @return
     */
    @Override
    public ResultVo deleteBookCategory(BookCategory bookCategory) {
        QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", bookCategory.getIsbn());
        int delete = bookCategoryDao.delete(queryWrapper);
        // 创建ResultVo

        ResultVo resultVo = new ResultVo();
        if (delete == 1) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败！");
            return resultVo;
        }
    }


    /**
     * 批量删除图书类别
     */
    public ResultVo deleteAllBookCategory(List<String> isbns) {
        LambdaQueryWrapper<BookCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BookCategory::getIsbn, isbns);
        int delete = bookCategoryDao.delete(lambdaQueryWrapper);
        ResultVo resultVo = new ResultVo();

        if (delete != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败！");
            return resultVo;
        }
    }


    @Override
    public ResultVo updateBookCategory(BookCategory bookCategory) {
        QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", bookCategory.getIsbn());
        int update = bookCategoryDao.update(bookCategory, queryWrapper);
        ResultVo resultVo = new ResultVo();
        if (update != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("修改成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("修改失败！");
            return resultVo;
        }
    }
}
