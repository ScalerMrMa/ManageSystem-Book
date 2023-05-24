package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.dao.BookSelfDao;
import com.it.domain.BookInfo;
import com.it.domain.BookMyself;
import com.it.service.BookSelfService;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Service
public class BookSelfServiceImpl implements BookSelfService {
    @Autowired
    BookSelfDao bookSelfDao;
    @Override
    public ResultVo addToBookSelf(BookInfo bookInfo, HttpSession httpSession) {
        return null;
    }

    @Override
    public DataVo<BookMyself> getBookMyBooks(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        // 创建条件构造器
        LambdaQueryWrapper<BookMyself> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BookMyself::getEmail, email);

        //  创建结果返回集
        DataVo<BookMyself> bookSelfDataVo= new DataVo<>();
        try {

                // 查询条目数
                Integer count = Math.toIntExact(bookSelfDao.selectCount(lambdaQueryWrapper));
                // 查询结果
                List<BookMyself> bookSelfList = bookSelfDao.selectList(lambdaQueryWrapper);
                bookSelfDataVo.setCode(0);
                bookSelfDataVo.setMsg("查询成功！");
                bookSelfDataVo.setCount(count);
                bookSelfDataVo.setData(bookSelfList);

        }catch (Exception e) {
            e.printStackTrace();
            bookSelfDataVo.setCode(3);
            bookSelfDataVo.setMsg("查询失败！");
            bookSelfDataVo.setCount(0);
            bookSelfDataVo.setData(null);
        }
        return bookSelfDataVo;
    }
}
