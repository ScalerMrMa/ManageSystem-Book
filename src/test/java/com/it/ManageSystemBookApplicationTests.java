package com.it;

import com.it.dao.BookInfoDao;
import com.it.domain.BookInfo;
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

    @Test
    public void getBookInfo() {
        List<BookInfo> bookInfos = bookInfoDao.selectList(null);
        for (BookInfo bookInfo : bookInfos) {
            System.out.println(bookInfo);
        }
    }
}
