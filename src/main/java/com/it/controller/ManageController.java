package com.it.controller;

import com.it.domain.BookInfo;
import com.it.service.BookInfoService;
import com.it.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/bookList")
    @ResponseBody
    public DataVo<BookInfo> bookList() {
        log.println("访问成功");

        return bookInfoService.getBookInfoList();
    }

}
