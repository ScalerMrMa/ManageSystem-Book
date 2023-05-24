package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.it.dao.AnnouncementDao;
import com.it.domain.Announcement;
import com.it.service.AnnouncementService;
import com.it.util.SnowflakeIdGenerator;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;


    // 查询功能
    @Override
    public DataVo<Announcement> selectAnnouncement() {
        // 创建结果类
        DataVo<Announcement> dataVo = new DataVo<>();
        dataVo.setCode(0);  // 状态码
        dataVo.setMsg("");

        Integer count = Math.toIntExact(announcementDao.selectCount(null));
        dataVo.setCount(count);

        List<Announcement> announcements = announcementDao.selectList(null);
        dataVo.setData(announcements);

        return dataVo;
    }

    // 发布功能
    @Override
    public ResultVo insertAnnouncement(Announcement announcement) {
        // 获取当前的时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        // 随机生成ID
        String generate = SnowflakeIdGenerator.generate();

        // 获取科室信息
        // 创造返回结果类
        ResultVo resultVo = new ResultVo();
        try {
            // 公告发布的时间
            announcement.setPublishNumber(generate);
            announcement.setPublishDate(format);
            // 公告更新时间
            announcement.setEditDate(format);
            announcement.setPublishStatus("正常");
            // 插入数据
            int insert = announcementDao.insert(announcement);
            if (insert != 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }
        return resultVo;
    }

    // 删除功能
    public ResultVo forbidStatus(Integer publishNumber) {
        // 条件构造器
        LambdaUpdateWrapper<Announcement> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Announcement::getPublishNumber, publishNumber);
        // 根据id查询出公告
        Announcement announcement = announcementDao.selectOne(lambdaUpdateWrapper);
        lambdaUpdateWrapper.set(Announcement::getPublishStatus, "失效");

        // 构造操作结果
        ResultVo resultVo = new ResultVo();
        // 更新字段
        try {
            if (announcement.getPublishStatus().equals("失效")) {
                resultVo.setCode(1);
                resultVo.setMsg("该公告已失效！");
                return resultVo;
            }
            int update = announcementDao.update(announcement, lambdaUpdateWrapper);
            if (update != 0) {
                resultVo.setCode(0);
                resultVo.setMsg("操作成功！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
        }

        return resultVo;
    }

    // 批量删除
    public ResultVo forbidManyStatus(List<Integer> numbers) {
        // 创建条件构造器
        LambdaUpdateWrapper<Announcement> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Announcement::getPublishNumber, numbers)
                .set(Announcement::getPublishStatus, "失效");
        int delete = announcementDao.delete(lambdaUpdateWrapper);

        ResultVo resultVo = new ResultVo();
        if (delete !=0 ) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败！");
            return resultVo;
        }
    }

    /**
     * 用户端获取公告
     * @return
     */
    @Override
    public DataVo<Announcement> getAnnouncements() {
        // 创建条件构造器
        LambdaQueryWrapper<Announcement> lambdaQueryWrapper = new LambdaQueryWrapper<Announcement>();
        lambdaQueryWrapper.eq(Announcement::getPublishStatus, "正常");

        // 创建返回结果
        DataVo<Announcement> dataVo = new DataVo<>();
        try {
            List<Announcement> announcements = announcementDao.selectList(lambdaQueryWrapper);
            if (announcements.size() > 0) {
                dataVo.setCode(0);
                dataVo.setMsg("查询成功！");
                dataVo.setCount(announcements.size());
                dataVo.setData(announcements);
            }else {
                dataVo.setCode(0);
                dataVo.setMsg("暂无公告！");
                dataVo.setCount(0);
                dataVo.setData(announcements);
            }
        }catch (Exception e) {
            e.printStackTrace();
            dataVo.setCode(3);
            dataVo.setMsg("查询成功！");
            dataVo.setCount(0);
            dataVo.setData(null);
        }
        return dataVo;
    }
}
