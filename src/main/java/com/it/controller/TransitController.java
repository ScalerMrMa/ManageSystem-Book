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

    /**
     * 访问templates下的bookCategory.html文件
     * @return
     */
    @GetMapping("bookCategory")
    public String toBookCategory() {
        return "admin/bookCategory";
    }

    /**
     * 图书借阅信息查询
     * @return
     */
    @GetMapping("borrowInfoSearch")
    public String toBorrowInfo() {
        return "admin/borrowInfoSearch";
    }

    /**
     * 借阅规则管理
     * @return
     */
    @GetMapping("borrowRuleManage")
    public String toBorrowRuleManage() {
        return "admin/borrowRuleManage";
    }

    /**
     * 公告模块管理
     * @return
     */
    @GetMapping("announcement")
    public String toAnnoncement() {
        return "admin/announcement";
    }
}
