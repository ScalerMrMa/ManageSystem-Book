package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.it.dao.ReaderInfoDao;
import com.it.domain.ReaderInfo;
import com.it.service.UserService;
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
public class UserServiceImpl implements UserService {
   
   @Autowired
   private ReaderInfoDao readerInfoDao;


   /**
    * 获取管理员信息
    * @param WorkUserName
    * @return
    */
   @Override
   public DataVo<ReaderInfo> getWorkUsers(String WorkUserName) {
      // 条件构造器
      LambdaQueryWrapper<ReaderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      // 创建返回结果类
      DataVo<ReaderInfo> dataVo = new DataVo<>();
      Integer count = 0;
      // 工作人员信息集合
      List<ReaderInfo> readerInfoList = null;
      try {
         // 判断是否进行搜索
         if (WorkUserName != null) {
            lambdaQueryWrapper.like(ReaderInfo::getUsername, WorkUserName);
         }
         readerInfoDao.selectList(lambdaQueryWrapper);
         readerInfoList = readerInfoDao.selectList(lambdaQueryWrapper);
         count = Math.toIntExact(readerInfoDao.selectCount(lambdaQueryWrapper));
         dataVo.setCode(0);
         dataVo.setMsg("查询成功");
         dataVo.setCount(count);
         dataVo.setData(readerInfoList);
      }catch (Exception e) {
         e.printStackTrace();
         dataVo.setCode(-1);
         dataVo.setMsg("查询异常");
         dataVo.setCount(0);
         dataVo.setData(null);
      }

      return dataVo;
   }

   /**
    * 禁用用户
    * @param WorkUserId
    * @return
    */
   @Override
   public ResultVo forbidUser(String WorkUserId) {
      // 条件构造器
      LambdaUpdateWrapper<ReaderInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      lambdaUpdateWrapper.eq(ReaderInfo::getReaderId, WorkUserId);
      // 根据id查询出用户
      ReaderInfo readerInfo = readerInfoDao.selectOne(lambdaUpdateWrapper);

      // 构造操作结果
      ResultVo resultVo = new ResultVo();
      // 更新字段
      try {
         if (readerInfo.getStatus().equals("禁用")) {
            resultVo.setCode(1);
            resultVo.setMsg("当前用户已禁用！");
         }else {
            lambdaUpdateWrapper.set(ReaderInfo::getStatus, "禁用");
            int update = readerInfoDao.update(readerInfo, lambdaUpdateWrapper);
            if (update != 0) {
               resultVo.setCode(0);
               resultVo.setMsg("已禁用！");
            }else {
               resultVo.setCode(3);
               resultVo.setMsg("操作失败！");
            }
         }
      }catch (Exception e) {
         e.printStackTrace();
         resultVo.setCode(3);
         resultVo.setMsg("操作失败！");
      }

      return resultVo;
   }

   /**
    * 启用用户
    * @param userId
    * @return
    */
   @Override
   public ResultVo activeUser(String userId) {
      // 条件构造器
      LambdaUpdateWrapper<ReaderInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      lambdaUpdateWrapper.eq(ReaderInfo::getReaderId, userId);
      // 根据id查询出用户
      ReaderInfo readerInfo = readerInfoDao.selectOne(lambdaUpdateWrapper);

      // 构造操作结果
      ResultVo resultVo = new ResultVo();
      // 更新字段
      try {
         if (readerInfo.getStatus().equals("启用")) {
            resultVo.setCode(1);
            resultVo.setMsg("当前用户已启用！");
         }else{
            lambdaUpdateWrapper.set(ReaderInfo::getStatus, "启用");
            int update = readerInfoDao.update(null, lambdaUpdateWrapper);
            if (update != 0) {
               resultVo.setCode(0);
               resultVo.setMsg("已启用！");
            }else {
               resultVo.setCode(2);
               resultVo.setMsg("启用失败！");
            }
         }

      }catch (Exception e) {
         e.printStackTrace();
         resultVo.setCode(2);
         resultVo.setMsg("启用失败！");
      }

      return resultVo;
   }

}
