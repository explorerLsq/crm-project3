package com.yang.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liushiqiang05
 * @date 2022/9/15 12:32 AM
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        //请求转发
        return "index";
    }
}
