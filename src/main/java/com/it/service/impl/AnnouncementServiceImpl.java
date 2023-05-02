package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.dao.AnnouncementDao;
import com.it.domain.Announcement;
import com.it.service.AnnouncementService;
import com.it.util.RandomNumberIdGenerator;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Integer count = announcementDao.selectCount(null);
        dataVo.setCount(count);

        List<Announcement> announcements = announcementDao.selectList(null);
        dataVo.setData(announcements);

        return dataVo;
    }

    // 发布功能
    @Override
    public ResultVo insertAnnouncement(Announcement announcement) {
        // 随机生成一个6位的id
        Integer id = Integer.parseInt(RandomNumberIdGenerator.generateRandomNumberId());
        announcement.setPublishNumber(id);
        int insert = announcementDao.insert(announcement);

        // 创建返回结果类
        ResultVo resultVo = new ResultVo();
        if (insert != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("发布成功！");
            return resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("发布失败");
            return resultVo;
        }
    }

    // 删除功能
    public ResultVo deleteAnnouncement(Integer publishNumber) {
        // 创建条件构造器
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_number", publishNumber);

        int delete = announcementDao.delete(queryWrapper);

        // 创建返回消息内容
        ResultVo resultVo = new ResultVo();
        if (delete != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("删除成功！");
            return  resultVo;
        }else {
            resultVo.setCode(1);
            resultVo.setMsg("删除失败！");
            return resultVo;
        }
    }

    // 批量删除
    public ResultVo deleteManyAnnouncement(List<Integer> numbers) {
        // 创建条件构造器
        LambdaQueryWrapper<Announcement> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Announcement::getPublishNumber, numbers);
        int delete = announcementDao.delete(lambdaQueryWrapper);

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
}
