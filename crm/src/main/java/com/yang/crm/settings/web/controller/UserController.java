package com.yang.crm.settings.web.controller;

import com.yang.crm.commons.constants.Constants;
import com.yang.crm.commons.domain.ReturnObject;
import com.yang.crm.commons.utils.CookieUtils;
import com.yang.crm.commons.utils.DateUtils;
import com.yang.crm.settings.domain.User;
import com.yang.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liushiqiang05
 * @date 2022/9/15 12:59 AM
 */
@Controller
public class UserController {

    @Resource
    private UserService userService;
    /**
     * URL要和controller方法处理完请求后，响应信息返回的页面的资源目录保持一致
     * @return
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 封装前端传来的参数，用于数据库的查询条件
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        // 通过封装的map从数据库中查询对应的User对象
        User user = userService.queryUserByLoginActAndPwd(map);
        // 返回给前端的 后端响应信息的封装类
        ReturnObject returnObject = new ReturnObject();
        // 由查询结果生成相应信息
        if (user == null) { // 用户信息为空，用户名或者密码错误
            returnObject.setMessage("用户名或者密码错误");
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
        } else { // 用户信息存在，判断当前用户是否符合登录条件
            // 获取当前时间
            String nowTime = DateUtils.formatDateTime(new Date());
            if (nowTime.compareTo(user.getExpireTime()) > 0) {
                // 当前时间大于该用户有效时间，登陆失败，该用户已过期
                returnObject.setMessage("该用户已过期");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            } else if ("0".equals(user.getLockState())) {
                // 登陆失败，该用户被锁定
                returnObject.setMessage("该用户被锁定");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                // 登录失败，该用户ip受限 （request.getLocalAddr()是获取本机ip）
                returnObject.setMessage("该用户ip受限");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            } else {
                /**
                 * 作用域
                 * pageContext: 同一个页面
                 * request: 同一次请求
                 * session: 同一个浏览器
                 * application:  同一个应用
                */
                // 登录成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                // 将当前的User对象存入session中，用于不同请求路径下的前端页面调用
                session.setAttribute(Constants.SESSION_USER, user); // 比如：在登录后的页面右上角显示登录用户信息或其他页面调用需求
                // 如果需要记住密码，则创建对应的cookie对象（不安全，如何解决？）
                if ("true".equals(isRemPwd)) {
                    // 检验当前是否已经存在对应的账号和密码cookie，如果存在则不再创建
                    Cookie[] cookies = request.getCookies();
                    // 判断是否是一个新用户，isHaveCookie用来标记是否存在当前用户的cookie
                    boolean isHaveCookie = CookieUtils.findCookieByValue(cookies, user.getId());
                    // 如果不存在cookie，那么就创建当前新用户的cookie
                    if (!isHaveCookie) {
                        CookieUtils.createLoginCookie(user, response);
                    }
                } else { // 如果没有勾选记住密码，则将当前Cookie删除（重置同名的cookie存活时间达到删除的效果）
                    CookieUtils.destroyLoginCookie(response);
                }
            }
        }
        return returnObject;
    }
}
