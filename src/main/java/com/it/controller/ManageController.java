package com.it.controller;

import com.it.domain.*;
import com.it.service.*;
import com.it.statistics.DataItem;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    DataStatisticsService dataStatisticsService;

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

    @Autowired
    private WorkUserService workUserService;

    // ----------------------------------查询专区---------------------------------------------------
    /**
     * 获取图书列表
     * @return
     */
    @GetMapping("/bookList")
    @ResponseBody
    public DataVo<BookInfo> bookList(String bookName) {
        System.out.println("信息：" + bookName);
        return bookInfoService.getBookInfos(bookName);
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

    /**
     * 获取图书管理员信息
     * @param workUserName
     * @return
     */
    @GetMapping("/getWorkUsers")
    @ResponseBody
    public DataVo<WorkUser> getWorkUsers(String workUserName) {
        return workUserService.getWorkUsers(workUserName);
    }
    //------------------------------------添加功能---------------------------------------------

    /**
     * 添加员工的信息
     * @param workUser
     * @return
     */
    @RequestMapping("/addWorkUserInfo")
    @ResponseBody
    public ResultVo addInnerUserInfo(WorkUser workUser) {

        return workUserService.addWorkUserInfo(workUser);
    }


    /**
     * 添加图书信息
     * @param bookInfo
     * @return
     */
    @RequestMapping(value = "/insertBookInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBook(BookInfo bookInfo) {
        return  bookInfoService.addBookInfo(bookInfo);
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
    // ----------------------------------状态修改专区-----------------------------


    /**
     * 下架图书
     * @param isbn
     * @return
     */
    @RequestMapping( "/forbidBooks")
    @ResponseBody
    public ResultVo deleteBookInfo(String isbn) {
        return bookInfoService.forbidBookInfo(isbn);
    }

    /**
     * 上架图书
     * @param isbn
     * @return
     */
    @RequestMapping("/activeBooks")
    @ResponseBody
    public ResultVo activeBookInfo(String isbn) {
        return bookInfoService.activeBookInfo(isbn);
    }

    /**
     * 批量删除图书信息
     * @param isbns
     * @return
     */
    @RequestMapping("/forbidManyBookInfo")
    @ResponseBody
    public ResultVo deleteAllBookInfo(@RequestParam("isbn") List<String> isbns) {
        return bookInfoService.forbidBookInfos(isbns);
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
    @RequestMapping("/forbidManyBorrowRules")
    @ResponseBody
    public ResultVo deleteAllBorrowManage(@RequestParam("number") List<Integer> numbers) {

        return borrowRuleManageService.forbidManyBorrowRules(numbers);
    }

    /**
     * 删除单条图书借阅管理规则
     * @param number
     * @return
     */
    @RequestMapping("/forbidBorrowStatus")
    @ResponseBody
    public ResultVo forbidBorrowStatus(Integer number) {

        return borrowRuleManageService.forbidBorrowStatus(number);
    }


    @RequestMapping("forbidStatus")
    @ResponseBody
    public ResultVo forbidStatus(Integer publishNumber) {
        return announcementService.forbidStatus(publishNumber);
    }

    /**
     * 根据发布序号批量将发布内容状态设置为失效
     * @param     @ResponseBody
     *     public ResultVo deleteAnnouncement(Integer publishNumber) {
     * @return
     */
    @RequestMapping("forbidManyStatus")
    @ResponseBody
    public ResultVo forbidManyStatus(@RequestParam("publishNumber") List<Integer> publishNUmbers) {
        return announcementService.forbidManyStatus(publishNUmbers);
    }

    /**
     * 批量禁用工作人员账户状态
     * @param workUserIds
     * @return
     */
    @RequestMapping("/forbidWorkUsers")
    @ResponseBody
    public ResultVo forbidWorkUsers(@RequestParam("workUserId") List<String> workUserIds) {
        return workUserService.forbidWorkUsers(workUserIds);
    }

    /**
     * 激活用户
     * @param workUserId
     * @return
     */
    @RequestMapping("/activeWorkUser")
    @ResponseBody
    public ResultVo activeWorkUser(@RequestParam("workUserId") String workUserId) {

        return workUserService.activeWorkUser(workUserId);
    }
    /**
     * 禁用用户
     * @param workUserId
     * @return
     */
    @RequestMapping("/forbidWorkUser")
    @ResponseBody
    public ResultVo forbidWorkUser(@RequestParam("workUserId") String workUserId) {
        return workUserService.forbidWorkUser(workUserId);
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

    /**
     * 修改图书管理员信息
     * @param workUser
     * @return
     */
    @RequestMapping("/updateWorkUserInfo")
    @ResponseBody
    public ResultVo updateWorkUserInfo(WorkUser workUser) {
        return workUserService.updateWorkUserInfo(workUser);
    }

    //  -------------------------------------------数据展示专区----------------------------------

    /**
     * 图书数量展示
     * @return
     */
    @RequestMapping("/ECharting")
    @ResponseBody
    public Map<String, List<DataItem>> viewData() {
        Map<String, List<DataItem>> response = new HashMap<>();
        System.out.println(response);
        List<DataItem> source = dataStatisticsService.statistic();
        response.put("dataset", source);
        return response;
    }

}
