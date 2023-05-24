package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.dao.BookInfoDao;
import com.it.dao.ReaderInfoDao;
import com.it.dao.UserDao;
import com.it.dao.WorkUserDao;
import com.it.domain.BookInfo;
import com.it.domain.ReaderInfo;
import com.it.domain.User;
import com.it.domain.WorkUser;
import com.it.service.DataStatisticsService;
import com.it.statistics.DataItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description   用于数据统计
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {

    @Autowired
    private ReaderInfoDao readerInfoDao;
    @Autowired
    private WorkUserDao workUserDao;

    @Autowired
    private BookInfoDao bookInfoDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Integer> dataCounts() {
        // 获取图书总数,只搜索出来上架的图书
        LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BookInfo::getBookStatus, "上架");
        List<Integer> list = new ArrayList<>();
        // 创建条件构造器
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 创建查询出版社的条件构造器

        LambdaQueryWrapper<BookInfo> publishQueryWrapper = new LambdaQueryWrapper<>();
        publishQueryWrapper.select(BookInfo::getPublisher)
                .groupBy(BookInfo::getPublisher);

        // 创建查询作者条件构造器
        LambdaQueryWrapper<BookInfo> authorQueryWrapper = new LambdaQueryWrapper<>();
        authorQueryWrapper.select(BookInfo::getAuthor)
                .groupBy(BookInfo::getAuthor);
        // 查询图书总数
        Integer bookCount = Math.toIntExact(bookInfoDao.selectCount(lambdaQueryWrapper));
        list.add(bookCount);

        // 用户总数
        Integer userCount = Math.toIntExact(userDao.selectCount(userLambdaQueryWrapper));
        list.add(userCount);

        // 统计出版社数量
        List<BookInfo> publisherCount = bookInfoDao.selectList(publishQueryWrapper);
        list.add(publisherCount.size());

        // 统计作者数量
        List<BookInfo> authorCount = bookInfoDao.selectList(authorQueryWrapper);
        list.add(authorCount.size());
        return list;
    }

    @Override
    public List<DataItem> statistic() {
        // 创建条件构造器
        LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 将图书种类分类
        lambdaQueryWrapper.select(BookInfo::getBookCategory)
                .groupBy(BookInfo::getBookCategory);
        // 查询分类
        List<BookInfo> bookInfos = bookInfoDao.selectList(lambdaQueryWrapper);

        List<DataItem> list = new ArrayList<>();
        for (BookInfo bookInfo : bookInfos) {
            LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper
                    = new LambdaQueryWrapper<>();
            // 根据分类查询对应的图书数量
            bookInfoLambdaQueryWrapper.eq(BookInfo::getBookCategory, bookInfo.getBookCategory());
            Long count = bookInfoDao.selectCount(bookInfoLambdaQueryWrapper);

            list.add(new DataItem(count.intValue(), bookInfo.getBookCategory()));
        }

        return list;
    }

    public List<DataItem> dataStatistic() {


        // 创建条件构造器
        LambdaQueryWrapper<ReaderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 将图书种类分类
        lambdaQueryWrapper.eq(ReaderInfo::getStatus, "启用");
        // 查询分类
        Integer readerCount = Math.toIntExact(readerInfoDao.selectCount(lambdaQueryWrapper));

        LambdaQueryWrapper<WorkUser> workUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 将图书种类分类
        workUserLambdaQueryWrapper.eq(WorkUser::getWorkUserAccountStatus, "启用");
        Integer workCount = Math.toIntExact(workUserDao.selectCount(workUserLambdaQueryWrapper));

        List<DataItem> list = new ArrayList<>();

        list.add(new DataItem(readerCount, "用户数"));
        list.add(new DataItem(workCount, "员工数"));
        list.add(new DataItem(3201, "访问量"));

        return list;
    }

}
