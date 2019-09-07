package com.joker.test.spring.sql;

import com.joker.sql.dao.UserMapper;
import com.joker.sql.service.UserService;
import com.joker.test.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Joker Jing
 * @date: 2019/8/24
 */
public class Delete extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void delete() {
        userMapper.deleteById(69);
    }

}
