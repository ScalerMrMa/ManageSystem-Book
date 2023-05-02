package com.it.controller;

import com.it.domain.BookCategory;
import com.it.domain.BookInfo;
import com.it.domain.BorrowRuleManage;
import com.it.domain.BorrowingInformation;
import com.it.service.BookCategoryService;
import com.it.service.BookInfoService;
import com.it.service.BorrowInfoService;
import com.it.service.BorrowRuleManageService;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

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

    // ----------------------------------查询专区---------------------------------------------------
    /**
     * 获取图书列表
     * @return
     */
    @GetMapping("/bookList")
    @ResponseBody
    public DataVo<BookInfo> bookList() {
        log.println("访问成功");

        return bookInfoService.getBookInfoList();
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

    //------------------------------------添加功能---------------------------------------------

    /**
     * 添加图书信息
     * @param bookInfo
     * @return
     */
    @RequestMapping(value = "/insertBookInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBook(BookInfo bookInfo) {

        bookInfoService.insetBookInfo(bookInfo);
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("提交成功");
        return resultVo;
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

    @RequestMapping(value = "/insertBorrowRule", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBorrowRule(BorrowRuleManage borrowRuleManage) {
        System.out.println(borrowRuleManage);
        return borrowRuleManageService.addBorrowRuleManage(borrowRuleManage);
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
        System.out.println(isbn);
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
        System.out.println(isbn);
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
        System.out.println(numbers);
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
        System.out.println(number);
        return borrowRuleManageService.deleteBorrowRule(number);
    }
    // ------------------------------------修改专区---------------------------------------


    /**
     * 修改图书类别
     * @param bookCategory
     * @return
     */
    @RequestMapping("/updateCategory")
    @ResponseBody
    public ResultVo updateBookCategory(BookCategory bookCategory) {
        System.out.println(bookCategory);
        return bookCategoryService.updateBookCategory(bookCategory);
    }
}
