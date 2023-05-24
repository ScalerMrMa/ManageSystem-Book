package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.it.dao.WorkUserDao;
import com.it.domain.WorkUser;
import com.it.service.WorkUserService;
import com.it.util.SnowflakeIdGenerator;
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
public class WorkUserServiceImpl implements WorkUserService {
   
   @Autowired
   private WorkUserDao WorkUserDao;


   /**
    * 获取管理员信息
    * @param WorkUserName
    * @return
    */
   @Override
   public DataVo<WorkUser> getWorkUsers(String WorkUserName) {
      // 条件构造器
      LambdaQueryWrapper<WorkUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      // 创建返回结果类
      DataVo<WorkUser> dataVo = new DataVo<>();
      Integer count = 0;
      // 工作人员信息集合
      List<WorkUser> WorkUserList = null;
      try {
         // 判断是否进行搜索
         if (WorkUserName != null) {
            lambdaQueryWrapper.like(WorkUser::getUsername, WorkUserName);
         }
         WorkUserList = WorkUserDao.selectList(lambdaQueryWrapper);
         count = Math.toIntExact(WorkUserDao.selectCount(lambdaQueryWrapper));
         dataVo.setCode(0);
         dataVo.setMsg("查询成功");
         dataVo.setCount(count);
         dataVo.setData(WorkUserList);
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
    * 添加员工信息
    * @return
    */
   @Override
   public ResultVo addWorkUserInfo(WorkUser WorkUser) {
      // 使用雪花算法生成长度为10的userId

        String WorkUserId = SnowflakeIdGenerator.generate();
        WorkUser.setWorkUserId(WorkUserId);
      // 对用户密码进行加密
//      String WorkUserPassword = WorkUser.getWorkUserPassword();
//      String encryptPassword = PasswordUtils.hashPassword(WorkUserPassword);
//      WorkUser.setWorkUserPassword(encryptPassword);
      // 创造返回结果类
      ResultVo resultVo = new ResultVo();
      try {
         int insert = WorkUserDao.insert(WorkUser);
         if (insert != 0) {
            resultVo.setCode(0);
            resultVo.setMsg("添加成功！");
         }else {
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
         }
      }catch (Exception e) {
         e.printStackTrace();
         resultVo.setCode(3);
         resultVo.setMsg("操作失败！");
      }
      return resultVo;
   }

   /**
    * 批量禁用工作人员
    * @param workUserIds
    * @return
    */
   public ResultVo forbidWorkUsers(List<String> workUserIds) {
      // 创建条件构造器
      LambdaUpdateWrapper<WorkUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      lambdaUpdateWrapper.in(WorkUser::getWorkUserId, workUserIds)
              .set(WorkUser::getWorkUserAccountStatus, "禁用");
      LambdaQueryWrapper<WorkUser> WorkUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
      WorkUserLambdaQueryWrapper.in(WorkUser::getWorkUserId, workUserIds);
      // 获取对象
      List<WorkUser> WorkUserList = WorkUserDao.selectList(WorkUserLambdaQueryWrapper);
      // 创建结果集
      ResultVo resultVo = new ResultVo();
      try {
         for (WorkUser WorkUser : WorkUserList) {
            if (WorkUser.getWorkUserAccountStatus().equals("禁用")) {
               resultVo.setCode(1);
               resultVo.setMsg("部分用户已经被禁用！此操作无效");
               return resultVo;
            }
         }
         int updateCount = WorkUserDao.update(null, lambdaUpdateWrapper);
         if (updateCount > 0) {
            resultVo.setCode(0);
            resultVo.setMsg("操作成功！");
         }
      } catch (Exception e) {
         e.printStackTrace();
         resultVo.setCode(3);
         resultVo.setMsg("操作失败！");
      }
      return resultVo;
   }

   /**
    * 禁用用户
    * @param WorkUserId
    * @return
    */
   @Override
   public ResultVo forbidWorkUser(String WorkUserId) {
      // 条件构造器
      LambdaUpdateWrapper<WorkUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      lambdaUpdateWrapper.eq(WorkUser::getWorkUserId, WorkUserId);
      // 根据id查询出用户
      WorkUser workUser = WorkUserDao.selectOne(lambdaUpdateWrapper);

      // 构造操作结果
      ResultVo resultVo = new ResultVo();
      // 更新字段
      try {
         if (workUser.getWorkUserAccountStatus().equals("禁用")) {
            resultVo.setCode(1);
            resultVo.setMsg("当前用户已禁用！");
         }else {
            lambdaUpdateWrapper.set(WorkUser::getWorkUserAccountStatus, "禁用");
            int update = WorkUserDao.update(workUser, lambdaUpdateWrapper);
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
   public ResultVo activeWorkUser(String userId) {
      // 条件构造器
      LambdaUpdateWrapper<WorkUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      lambdaUpdateWrapper.eq(WorkUser::getWorkUserId, userId);
      // 根据id查询出用户
      WorkUser workUser = WorkUserDao.selectOne(lambdaUpdateWrapper);

      // 构造操作结果
      ResultVo resultVo = new ResultVo();
      // 更新字段
      try {
         if (workUser.getWorkUserAccountStatus().equals("启用")) {
            resultVo.setCode(1);
            resultVo.setMsg("当前用户已启用！");
         }else{
            lambdaUpdateWrapper.set(WorkUser::getWorkUserAccountStatus, "启用");
            int update = WorkUserDao.update(null, lambdaUpdateWrapper);
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

   /**
    * 修改用户
    * @param workUser
    * @return
    */
   @Override
   public ResultVo updateWorkUserInfo(WorkUser workUser) {
      System.out.println(workUser);
      // 创造条件构造器
      LambdaUpdateWrapper<WorkUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      lambdaUpdateWrapper.eq(WorkUser::getWorkUserId, workUser.getWorkUserId());
      // 通过Id获取用户的密码，再将用户的密码注入到前端传来的WorkUser
      WorkUser baseWorkUser = WorkUserDao.selectOne(lambdaUpdateWrapper);

      // 创建返回结果类
      ResultVo resultVo = new ResultVo();
      try {
         System.out.println(baseWorkUser.getWorkUserAccountStatus().equals("禁用"));
         if (baseWorkUser.getWorkUserAccountStatus().equals("禁用")) {
            resultVo.setCode(1);
            resultVo.setMsg("该用户已经被禁用！无法修改！");
            return resultVo;
         }
         // 如果前端传来的WorkUser的密码为null，那么就从数据库中将原来的密码查询出来
         if (workUser.getWorkUserPassword().equals("")) {

            String WorkUserPassword = baseWorkUser.getWorkUserPassword();
            workUser.setWorkUserPassword(WorkUserPassword);
         }
         int update = WorkUserDao.update(workUser, lambdaUpdateWrapper);
         if (update > 0) {
            resultVo.setCode(0);
            resultVo.setMsg("操作成功！");
         }else {
            resultVo.setCode(3);
            resultVo.setMsg("操作失败！");
         }
      }catch (Exception e) {
         resultVo.setCode(3);
         resultVo.setMsg("操作失败！");
      }

      return resultVo;
   }
//
//   /**
//    * 修改密码
//    * @param WorkUser
//    * @return
//    */
//   @Override
//   public ResultVo updateWorkUserSecret(WorkUser WorkUser) {
//      // 创建条件构造器
//      LambdaQueryWrapper<WorkUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//      lambdaQueryWrapper.eq(WorkUser::getWorkUserId, WorkUser.getWorkUserId());
//
//      // 将密码加密
//      String password = PasswordUtils.hashPassword(WorkUser.getWorkUserPassword());
//      // 创建结果返回集
//      ResultVo resultVo = new ResultVo();
//      try {
//         WorkUser WorkUserUpdate = WorkUserDao.selectOne(lambdaQueryWrapper);
//         WorkUserUpdate.setWorkUserPassword(password);
//         int i = WorkUserDao.updateById(WorkUserUpdate);
//         if (i > 0) {
//            resultVo.setCode(0);
//            resultVo.setMsg("操作成功！");
//         }else {
//            resultVo.setCode(-1);
//            resultVo.setMsg("操作失败！");
//         }
//      }catch (Exception e) {
//      }
//      return resultVo;
//   }
//
//   /**
//    * 获取管理员的信息
//    * @return
//    */
//   @Override
//   public WorkUser getManagerInfo(HttpSession session) {
//      System.out.println(session.getAttribute("WorkUserNo"));
//
//      //   创建条件构造器
//      LambdaQueryWrapper<WorkUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//
//      Object WorkUserNo = session.getAttribute("WorkUserNo");
//      lambdaQueryWrapper.eq(WorkUser::getWorkUserNo, WorkUserNo);
//      WorkUser WorkUser = null;
//      try {
//         WorkUser = WorkUserDao.selectOne(lambdaQueryWrapper);
//
//      }catch (Exception e) {
//         e.printStackTrace();
//      }
//      return WorkUser;
//   }
//
//   /**
//    * 修改个人信息
//    * @param httpSession
//    * @return
//    */
//   @Override
//   public ResultVo updatePerson(WorkUser WorkUser, HttpSession httpSession)  {
//      System.out.println(httpSession.getAttribute("WorkUserNo"));
//
//      //   创建条件构造器
//      LambdaUpdateWrapper<WorkUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//      Object WorkUserNo = httpSession.getAttribute("WorkUserNo");
//
//      lambdaUpdateWrapper.eq(WorkUser::getWorkUserNo, WorkUserNo)
//              .set(WorkUser::getWorkUserName, WorkUser.getWorkUserName())
//              .set(WorkUser::getWorkUserGender, WorkUser.getWorkUserGender())
//              .set(WorkUser::getWorkUserEmail, WorkUser.getWorkUserEmail())
//              .set(WorkUser::getWorkUserPhone, WorkUser.getWorkUserPhone());
//      ResultVo resultVo = new ResultVo();
//      try {
//
//         int update = WorkUserDao.update(WorkUser, lambdaUpdateWrapper);
//         if (update > 0) {
//            resultVo.setCode(0);
//            resultVo.setMsg("操作成功！");
//         }
//      }catch (Exception e) {
//         e.printStackTrace();
//         resultVo.setCode(3);
//         resultVo.setMsg("操作失败！");
//      }
//      return resultVo;
//   }
//
//   // 修改密码
//   @Override
//   public ResultVo confirmPwd(String confirmPwd, HttpSession httpSession) {
//      // 创建查询条件构造器
//      LambdaQueryWrapper<WorkUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//      // 查询出数据库对象的密码，与前端传来的数据进行比对
//
//      //   创建条件构造器
//      LambdaUpdateWrapper<WorkUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//      Object WorkUserNo = httpSession.getAttribute("WorkUserNo");
//
//      lambdaQueryWrapper.eq(WorkUser::getWorkUserNo, WorkUserNo);
//
//      lambdaUpdateWrapper.eq(WorkUser::getWorkUserNo, WorkUserNo)
//              .set(WorkUser::getWorkUserPassword, confirmPwd);
//      ResultVo resultVo = new ResultVo();
//      try {
//         // 根据WorkUserNo查询出数据库对象
//         WorkUser WorkUser = WorkUserDao.selectOne(lambdaQueryWrapper);
//
//         if (WorkUser.getWorkUserPassword().equals(confirmPwd)) {
//            int update = WorkUserDao.update(null, lambdaUpdateWrapper);
//
//            resultVo.setCode(0);
//            resultVo.setMsg("操作成功！");
//
//         }else {
//            resultVo.setCode(2);
//            resultVo.setMsg("当前密码错误！");
//         }
//
//
//
//      }catch (Exception e) {
//         e.printStackTrace();
//         resultVo.setCode(3);
//         resultVo.setMsg("操作失败！");
//      }
//      return resultVo;
//   }
//
//   // 获取员工数目
//   @Override
//   public Integer getWorkUserCount() {
//      Integer WorkUserCount = WorkUserDao.selectCount(null);
//      return WorkUserCount;
//   }
}
