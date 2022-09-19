package com.yang.crm.settings.service;

import com.yang.crm.settings.domain.User;

import java.util.Map;

/**
 * @author liushiqiang05
 * @date 2022/9/17 4:27 PM
 */
public interface UserService {
    User queryUserByLoginActAndPwd(Map<String, Object> map);
}
