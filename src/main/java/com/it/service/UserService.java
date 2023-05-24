package com.it.service;

import com.it.domain.ReaderInfo;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface UserService {

    // 获取员工用户的信息
    public DataVo<ReaderInfo> getWorkUsers(String WorkUserName);
    // 禁用工作人员
    public ResultVo forbidUser(String readerId);
    // 启用工作人员
    public ResultVo activeUser(String readerId);
}
