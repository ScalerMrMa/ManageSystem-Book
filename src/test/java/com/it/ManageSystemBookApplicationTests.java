package com.it;

import com.it.dao.BookInfoDao;
import com.it.service.BookInfoService;
import com.it.service.BorrowInfoService;
import com.it.service.BorrowRuleManageService;
import com.it.service.DataStatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManageSystemBookApplicationTests {

    @Autowired
    DataStatisticsService dataStatisticsService;
    @Test
    void contextLoads() {
        dataStatisticsService.statistic();

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
