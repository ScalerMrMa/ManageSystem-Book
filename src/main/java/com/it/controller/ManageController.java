package com.it.controller;

import com.it.domain.*;
import com.it.service.*;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */

@CrossOrigin  // 解决跨域问题
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private BorrowInfoService borrowInfoService;

    @Autowired
    private BorrowRuleManageService borrowRuleManageService;

    @Autowired
    private AnnouncementService announcementService;

    // ----------------------------------查询专区---------------------------------------------------
    /**
     * 获取图书列表
     * @return
     */
    @GetMapping("/bookList")
    @ResponseBody
    public DataVo<BookInfo> bookList(String bookName) {
        System.out.println(bookName);
        return bookInfoService.getBookInfoList(bookName);
    }

    /**
     * 查询书籍分类
     * @return
     */
    @GetMapping("/bookCategory")
    @ResponseBody
    public DataVo<BookCategory> bookCategories() {
        return bookCategoryService.getBookCategory();
    }

    /**
     * 借阅信息查询,模糊搜索用户id和图书名字
     * @return
     */
    @GetMapping("/borrowInfoSearch")
    @ResponseBody
    public DataVo<BorrowingInformation> GetBorrowInfo(String readerId,String bookName) {
        return borrowInfoService.getBorrowInfo(readerId,bookName);
    }

    /**
     * 图书规则查询
     * @return
     */
    @GetMapping("/borrowRuleManage")
    @ResponseBody
    public DataVo<BorrowRuleManage> getBorrowRuleManage() {
        return borrowRuleManageService.getBorrowRuleManage();
    }

    @GetMapping("/announcementList")
    @ResponseBody
    public DataVo<Announcement> getAnnouncements() {
        return announcementService.selectAnnouncement();
    }

    //------------------------------------添加功能---------------------------------------------

    /**
     * 添加图书信息
     * @param bookInfo
     * @return
     */
    @RequestMapping(value = "/insertBookInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBook(BookInfo bookInfo) {
        return  bookInfoService.insetBookInfo(bookInfo);
    }

    /**
     * 添加图书类别信息
     * @param bookCategory
     * @return
     */
    @RequestMapping(value = "/insertBookCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBookCategory(BookCategory bookCategory) {
        return bookCategoryService.insertBookCategory(bookCategory);
    }

    /**
     * 添加图书借阅规则
     * @param borrowRuleManage
     * @return
     */
    @RequestMapping(value = "/insertBorrowRule", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBorrowRule(BorrowRuleManage borrowRuleManage) {
        return borrowRuleManageService.addBorrowRuleManage(borrowRuleManage);
    }

    /**
     * 发布公告
     * @param announcement
     * @return
     */
    @RequestMapping(value = "insertAnnouncement", method = RequestMethod.POST)
    @ResponseBody
     public ResultVo insertAnnouncement(Announcement announcement) {
        return announcementService.insertAnnouncement(announcement);
     }
    // ----------------------------------删除专区-----------------------------


    /**
     * 删除图书信息
     * @param isbn
     * @return
     */
    @RequestMapping( "/deleteBookInfo")
    @ResponseBody
    public ResultVo deleteBookInfo(String isbn) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setIsbn(isbn);
        return bookInfoService.deleteBookInfo(bookInfo);
    }

    /**
     * 批量删除图书信息
     * @param isbns
     * @return
     */
    @RequestMapping("/deleteAllBookInfo")
    @ResponseBody
    public ResultVo deleteAllBookInfo(@RequestParam("isbn") List<String> isbns) {
        return bookInfoService.deleteAllBookInfo(isbns);
    }

    /**
     * 单个删除图书类别
     * @param isbn
     * @return
     */
    @RequestMapping("/deleteBookCategory")
    @ResponseBody
    public ResultVo deleteBookCategory(String isbn) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setIsbn(isbn);

        return bookCategoryService.deleteBookCategory(bookCategory);
    }

    /**
     * 批量删除图书类别
     * @param isbns
     * @return
     */
    @RequestMapping("/deleteAllBokkCategory")
    @ResponseBody
    public ResultVo deleteAllBookCategory(@RequestParam("isbn") List<String> isbns) {
        return bookCategoryService.deleteAllBookCategory(isbns);
    }

    /**
     * 批量删除图书借阅规则
     * @param numbers
     * @return
     */
    @RequestMapping("/deleteAllBookCategory")
    @ResponseBody
    public ResultVo deleteAllBorrowManage(@RequestParam("number") List<Integer> numbers) {

        return borrowRuleManageService.deleteAllBorrowManage(numbers);
    }

    /**
     * 删除单条图书借阅管理规则
     * @param number
     * @return
     */
    @RequestMapping("/deleteBorrowRule")
    @ResponseBody
    public ResultVo deleteBorrowRule(Integer number) {

        return borrowRuleManageService.deleteBorrowRule(number);
    }

    /**
     * 根据发布序号删除发布内容
     * @param     @ResponseBody
     *     public ResultVo deleteAnnouncement(Integer publishNumber) {
     * @return
     */
    @RequestMapping("deleteAnnouncement")
    @ResponseBody
    public ResultVo deleteAnnouncement(Integer publishNumber) {
        return announcementService.deleteAnnouncement(publishNumber);
    }

    @RequestMapping("deleteManyAnnouncement")
    @ResponseBody
    public ResultVo deleteManyAnnouncement(@RequestParam("publishNumber") List<Integer> publishNUmbers) {
        return announcementService.deleteManyAnnouncement(publishNUmbers);
    }
    // ------------------------------------修改专区---------------------------------------

    /**
     * 图书信息修改
     * @param bookInfo
     * @return
     */
    @RequestMapping("/updateBookInfo")
    @ResponseBody
    public ResultVo updateBookInfo(BookInfo bookInfo) {
        System.out.println(bookInfo);
        return bookInfoService.updateBookInfo(bookInfo);
    }

    /**
     * 图书类别信修改
     * @param bookCategory
     * @return
     */
    @RequestMapping("/updateBookCategory")
    @ResponseBody
    public ResultVo updateBookCateGory(BookCategory bookCategory) {
        return bookCategoryService.updateBookCategory(bookCategory);
    }

    /**
     * 图书借阅规则修改
     * @param borrowRuleManage
     * @return
     */
    @RequestMapping("/updateBorrowRules")
    @ResponseBody
    public ResultVo borrowRuleManage(BorrowRuleManage borrowRuleManage) {
        return borrowRuleManageService.borrowRuleManage(borrowRuleManage);
    }
}
