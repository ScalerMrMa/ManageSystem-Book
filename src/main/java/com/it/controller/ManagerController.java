package com.it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping("/manage")
public class ManagerController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("someone");
        return "测试一下";
    }
}
