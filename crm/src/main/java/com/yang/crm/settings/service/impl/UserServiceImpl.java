package com.yang.crm.settings.service.impl;

import com.yang.crm.settings.domain.User;
import com.yang.crm.settings.mapper.UserMapper;
import com.yang.crm.settings.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liushiqiang05
 * @date 2022/9/17 4:29 PM
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }

}
