package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.dao.WorkUserDao;
import com.it.domain.WorkUser;
import com.it.service.DataStatisticsService;
import com.it.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("/")
public class TransitController {
    @Autowired
    private DataStatisticsService dataStatisticsService;

    @Autowired
    private WorkUserDao workUserDao;

    @Autowired
    private LogService logService;
    /**
     * 返回到templates下的homepage.html
     * @return  搜索出来的数据展示到页面
     */
    @GetMapping("homepage")
    public ModelAndView toHomePage() {
        ModelAndView modelAndView = new ModelAndView("admin/homepage");
        List<Integer> list = dataStatisticsService.dataCounts();
        // 将数据添加到modelAndVie中，将数据返回到前端
        modelAndView.addObject("bookCount",list.get(0));
        modelAndView.addObject("userCount", list.get(1));
        modelAndView.addObject("publishCount", list.get(2));
        modelAndView.addObject("authorCount", list.get(3));
        return modelAndView;
    }

    /**
     * 访问templates下的bookInfoManage.html文件
     * @return
     */
    @GetMapping("bookInfoManage")
    public String toBookInfoManage() {
        return "admin/bookInfoManage";
    }

    /**
     * 访问templates下的bookCategory.html文件
     * @return
     */
    @GetMapping("bookCategory")
    public String toBookCategory() {
        return "admin/bookCategory";
    }

    /**
     * 图书借阅信息查询
     * @return
     */
    @GetMapping("borrowInfoSearch")
    public String toBorrowInfo() {
        return "admin/borrowInfoSearch";
    }

    /**
     * 借阅规则管理
     * @return
     */
    @GetMapping("borrowRuleManage")
    public String toBorrowRuleManage() {
        return "admin/borrowRuleManage";
    }

    /**
     * 公告模块管理
     * @return
     */
    @GetMapping("announcement")
    public String toAnnoncement() {
        return "admin/announcement";
    }

    /**
     * 用户信息展示
     * @return
     */
    @GetMapping("showUserInfo")
    public ModelAndView toShowUserInfo(HttpSession httpSession) {
        // 创建条件构造器
        LambdaQueryWrapper<WorkUser> lambdaQueryWrapper = new LambdaQueryWrapper<WorkUser>();
        ModelAndView modelAndView = new ModelAndView("admin/showUserInfo");

        String email = (String)httpSession.getAttribute("email");
        String userNameByEmail = logService.getUserNameByEmail(email);

        // 根据用户民获取用户信息
        lambdaQueryWrapper.eq(WorkUser::getUsername, userNameByEmail);
        WorkUser workUser = workUserDao.selectOne(lambdaQueryWrapper);
        System.out.println(workUser);
        modelAndView.addObject("workUserId", workUser.getWorkUserId());
        modelAndView.addObject("username", workUser.getUsername());
        modelAndView.addObject("workUserRealName", workUser.getWorkUserRealName());
        modelAndView.addObject("workUserSex", workUser.getWorkUserSex());
        modelAndView.addObject("workUserAddress", workUser.getWorkUserAddress());
        modelAndView.addObject("workUserAge", workUser.getWorkUserAge());
        return modelAndView;
    }

    /**
     * 展示个人信息
     * @return
     */
    @GetMapping("showInfo")
    public String toShowInfo() {
        return "admin/showInfo";
    }

    /**
     * 修改个人信息
     */
    @GetMapping("setting")
    public String toSetting() {
        return "admin/setting";
    }

    /**
     * 工作人员管理
     * @return
     */
    @GetMapping("workUserManage")
    public String toWorkUserManage() {
        return "admin/workUserManage";
    }


    @GetMapping("updatePassword")
    public String updatePassword() {
        return "admin/updatePassword";
    }
}
