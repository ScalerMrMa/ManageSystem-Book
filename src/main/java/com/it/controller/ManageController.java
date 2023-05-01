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
     * 借阅信息查询
     * @return
     */
    @GetMapping("/borrowInfoSearch")
    @ResponseBody
    public DataVo<BorrowingInformation> GetBorrowInfo() {

        return borrowInfoService.getBorrowInfo();
    }

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

    @RequestMapping(value = "/insertBookCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo insertBookCategory(BookCategory bookCategory) {
        return bookCategoryService.insertBookCategory(bookCategory);
    }

    // ----------------------------------删除专区-----------------------------


    @RequestMapping(value = "/deleteBookInfo", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "*")
    public ResultVo deleteBookInfo(@RequestBody BookInfo bookInfo) {

        return null;
    }

    @RequestMapping("/deleteBookCategory")
    @ResponseBody
    public ResultVo deleteBokCategory(String isbn) {
        System.out.println(isbn);
        BookCategory bookCategory = new BookCategory();
        bookCategory.setIsbn(isbn);

        return bookCategoryService.deleteBookCategory(bookCategory);
    }
}
