package com.it.controller;

import com.it.dao.BookInfoDao;
import com.it.domain.BookInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("/manage")
public class ManagerController {


    @Resource
    BookInfoDao bookInfoDao;

    @GetMapping("/test")
    @ResponseBody
    public List<BookInfo> test() {
        List<BookInfo> bookInfos = bookInfoDao.selectList(null);
        System.out.println("图书信息");
        return bookInfos;
    }
}
