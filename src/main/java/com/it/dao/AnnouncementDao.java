package com.it.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.domain.Announcement;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MrMa
 * @version 1.0
 * @description   公告内容模块的基本操作
 */

@Mapper
public interface AnnouncementDao extends BaseMapper<Announcement> {
}
