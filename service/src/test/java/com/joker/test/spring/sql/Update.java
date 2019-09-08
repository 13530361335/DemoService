package com.joker.test.spring.sql;

import com.joker.entity.User;
import com.joker.mapper.UserMapper;
import com.joker.service.UserService;
import com.joker.test.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author Joker Jing
 * @date 2019/8/24
 */
public class Update extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void updateById() {
        User user = userMapper.selectById(127);
        user.setEmail("761878367@qq.com");
        userMapper.updateById(user);
    }

    @Test
    public void updateBatchById() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(103, 104, 105));
        users.forEach(user -> {
            user.setAccount(String.valueOf(user.getUserId()));
        });
        userService.updateBatchById(users);
    }

}
