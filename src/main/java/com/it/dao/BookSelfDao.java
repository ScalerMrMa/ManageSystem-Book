package com.it.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.domain.BookMyself;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Mapper
public interface BookSelfDao extends BaseMapper<BookMyself> {
}
