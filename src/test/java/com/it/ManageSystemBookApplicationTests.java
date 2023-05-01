package com.it;

import com.it.dao.BookInfoDao;
import com.it.domain.BookInfo;
import com.it.service.BookInfoService;
import com.it.service.BorrowInfoService;
import com.it.service.BorrowRuleManageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ManageSystemBookApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    BookInfoDao bookInfoDao;

    @Autowired
    BookInfoService bookInfoService;

    @Autowired
    BorrowInfoService borrowInfoService;

    @Autowired
    BorrowRuleManageService borrowRuleManageService;

    @Test
    public void getBookInfo() {
        List<BookInfo> bookInfos = bookInfoDao.selectList(null);
        for (BookInfo bookInfo : bookInfos) {
            System.out.println(bookInfo);
        }
    }


    @Test
    public void  getInf() {
        System.out.println(borrowInfoService.getBorrowInfo());
    }

    /**
     * 测试查询借阅规则
     */
    @Test
    public void  getBorrowRules() {
        System.out.println(borrowRuleManageService.getBorrowRuleManage());
    }

    /**
     * 测试删除功能
     */

    @Test
    public void testDelete() {

        System.out.println();
    }
}
