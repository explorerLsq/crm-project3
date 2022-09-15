package com.yang.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liushiqiang05
 * @date 2022/9/15 12:59 AM
 */
@Controller
public class UserController {
    /**
     * URL要和controller方法处理完请求后，响应信息返回的页面的资源目录保持一致
     * @return
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }
}
