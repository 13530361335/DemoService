package com.joker.sql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import com.joker.sql.service.UserService;
import org.springframework.stereotype.Service;

/**
 * created by Joker on 2019/7/28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
