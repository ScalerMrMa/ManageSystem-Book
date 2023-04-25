package com.it.controller;

import com.it.dao.BookInfoDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("/")
public class TransitController {


    @Resource
    BookInfoDao bookInfoDao;

    /**
     * 返回到templates下的homepage.html
     * @return
     */
    @GetMapping("homepage")
    public String toHomePage() {
        return "admin/homepage";
    }

    /**
     * 访问templates下的bookInfoManage.html文件
     * @return
     */
    @GetMapping("bookInfoManage")
    public String toBookInfoManage() {
        return "admin/bookInfoManage";
    }

    @GetMapping("bookCategory")
    public String toBookCategory() {
        return "admin/bookCategory";
    }


}
