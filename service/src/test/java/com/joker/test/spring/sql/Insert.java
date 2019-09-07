package com.joker.test.spring.sql;

import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import com.joker.sql.service.UserService;
import com.joker.test.spring.BaseTest;
import com.joker.util.JsonUtil;
import com.joker.util.LogUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Joker Jing
 * @date: 2019/8/24
 */
public class Insert extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        // 新增返回主键 主键需要加注解 @TableId(type = IdType.AUTO)
        User user = new User("xu", "123", "1", "1", "1");
        userMapper.insert(user);
        System.out.println(user.getUserId());
    }

    @Test
    public void saveBatch() {
        List<User> users = Arrays.asList(
                new User("xu", "123", "1", "1", "1"),
                new User("xu", "123", "1", "1", "1"),
                new User("xu", "123", "1", "1", "1"),
                new User("min", "123", "1", "1", "1"));
        userService.saveBatch(users);
        users.forEach(System.out::println);
    }

}
