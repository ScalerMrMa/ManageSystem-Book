package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.dao.UserDao;
import com.it.domain.*;
import com.it.service.*;
import com.it.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("/user")
public class userTransitController {

    @Autowired
    BorrowRuleManageService borrowRuleManageService;

    @Autowired
    BorrowInfoService borrowInfoService;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private BookSelfService bookSelfService;

    @Autowired
    private BookCategoryService bookCategoryService;

    /**
     * 用户的首页
     * @return
     */
    @GetMapping("/homepage")
    public ModelAndView toHomepage() {
        ModelAndView modelAndView = new ModelAndView("user/homepage");
        return modelAndView;
    }

    /**
     * 跳转到个人信息
     * @return
     */
    @GetMapping("/allwork")
    public ModelAndView toAllWork() {
        ModelAndView modelAndView = new ModelAndView("user/allwork");
        return modelAndView;
    }

    /**
     * 跳转我的书架
     * @return
     */
    @GetMapping("/mybookself")
    public ModelAndView toMyBookshelfPage(HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("email"));
        ModelAndView modelAndView = new ModelAndView("user/mybookself");
        // 创建条件构造器
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, httpSession.getAttribute("email"));

        User user = userDao.selectOne(lambdaQueryWrapper);
        modelAndView.addObject("userName", user.getUsername());

        return modelAndView;
    }

    /**
     * 跳转到个人信息
     * @return
     */
    @GetMapping("/profile")
    public ModelAndView toProfilePage() {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        return modelAndView;
    }

    @GetMapping("/showUserInfo")
    public ModelAndView toShowUserInfo() {
        ModelAndView modelAndView = new ModelAndView("user/showUserInfo");
        return modelAndView;
    }

    @GetMapping("/showInfo")
    public ModelAndView toShowInfo() {
        ModelAndView modelAndView = new ModelAndView("user/showInfo");

        return modelAndView;
    }

    @GetMapping("/setting")
    public ModelAndView toSetting() {
        ModelAndView modelAndView = new ModelAndView("user/setting");

        return modelAndView;
    }

    /**
     * 修改密码
     * @return
     */
    @GetMapping("/updatePassword")
    public ModelAndView toUpdatePassword() {
        ModelAndView modelAndView = new ModelAndView("user/updatePassword");
        return modelAndView;
    }

    @GetMapping("/report")
    public ModelAndView toAnnouncement() {
        ModelAndView modelAndView = new ModelAndView("user/report");
        return modelAndView;
    }
    // -----------------------------相关业务功能的实现----------------------------------

    /**
     * 根据前端搜索框返回的关键字查询数据
     * @param keyword
     * @return
     */
    @RequestMapping("/researchBooks")
    @ResponseBody
    public DataVo<BookInfo> getSearchBook(String keyword) {
        System.out.println(keyword);

        return bookInfoService.getSearchBookInfo(keyword);
    }

    /**
     * 获取图书的分类
     * @return
     */
    @RequestMapping("/getBookCategories")
    @ResponseBody
    public DataVo<BookCategory> getBookCategory() {
        return bookCategoryService.getBookCategory();
    }

    /**
     * 根据前端的返回的信息查询书籍
     * @param requestData
     * @return
     */
    @RequestMapping("/getCategoriesBook")
    @ResponseBody
    public DataVo<BookInfo> getCategoriesBook(@RequestBody String requestData) {
        // 对返回的字符串进行处理
        String substring = requestData.substring(13, requestData.length() - 2);
        System.out.println(substring);
        return bookInfoService.getCategoriesBook(substring);
    }

    @RequestMapping("/getMyBookSelf")
    @ResponseBody
    public DataVo<BookMyself> getMyBookSelf(HttpSession httpSession) {
        // 对返回的字符串进行处理

        return bookSelfService.getBookMyBooks(httpSession);
    }

    @RequestMapping("/getAnnouncement")
    @ResponseBody
    public DataVo<Announcement> getAnnouncement() {
        System.out.println(announcementService.getAnnouncements());
        return announcementService.getAnnouncements();
    }
    @RequestMapping("/getRules")
    @ResponseBody
    public DataVo<BorrowRuleManage> getRules() {

        return borrowRuleManageService.getRules();
    }
}
