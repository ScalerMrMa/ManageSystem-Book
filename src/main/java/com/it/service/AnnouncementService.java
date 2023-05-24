package com.it.service;

import com.it.domain.Announcement;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description 公告内容管理模块
 */
public interface AnnouncementService {

    // 查询功能
    DataVo<Announcement> selectAnnouncement();
    // 新增功能
    ResultVo insertAnnouncement(Announcement announcement);
    // 删除功能
    ResultVo forbidStatus(Integer publishNumber);
    // 批量删除功能
    ResultVo forbidManyStatus(List<Integer> integers);

    // 用户端获取公告
    public DataVo<Announcement> getAnnouncements();
}
