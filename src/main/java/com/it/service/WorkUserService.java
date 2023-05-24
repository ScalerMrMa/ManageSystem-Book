package com.it.service;

import com.it.domain.WorkUser;
import com.it.vo.DataVo;
import com.it.vo.ResultVo;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface WorkUserService {

    // 获取员工用户的信息
    public DataVo<WorkUser> getWorkUsers(String WorkUserName);
    // 添加员工用户信息
    public ResultVo addWorkUserInfo(WorkUser WorkUser);
    // 批量禁用工作人员
    public ResultVo forbidWorkUsers(List<String> WorkUserIds);
    // 禁用工作人员
    public ResultVo forbidWorkUser(String WorkUserId);
    // 启用工作人员
    public ResultVo activeWorkUser(String WorkUserId);
    // 修改工作人员信息
    public ResultVo updateWorkUserInfo(WorkUser WorkUser);
//    // 修改用户密码
//    public ResultVo updateWorkUserSecret(WorkUser WorkUser);
//
//    // 获取管理员的信息
//    public WorkUser getManagerInfo(HttpSession httpSession);
//
//    // 修改个人信息
//    public ResultVo updatePerson(WorkUser WorkUser, HttpSession httpSession);
//
//    // 修改密码
//    public ResultVo confirmPwd(String confirmPwd, HttpSession httpSession);
//
//    // 获取数目
//    public Integer getWorkUserCount();
}
