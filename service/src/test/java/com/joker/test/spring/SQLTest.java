package com.joker.test.spring;

import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import com.joker.test.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by Joker on 2019/7/12
 */
public class SQLTest extends SpringBootBaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }

}
