package com.joker.test.spring;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import com.joker.test.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * created by Joker on 2019/7/12
 */
public class SQLTest extends SpringBootBaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        User user1 = userMapper.selectById(1);
        System.out.println(user1);
    }

    @Test
    public void selectList() {
        QueryWrapper wrapper = new QueryWrapper<>()
//                .select("user_id","account","password")
//                .eq("account","jingmin")            // 等于
//                .ne("user_id",1)                    // 不等于
//                .in("user_id", Arrays.asList(1,2,3))
                .last("order by user_id desc")
                ;    // in
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    public void selectPage() {
        // 需要配置分页插件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("account","password")
                .eq("account", "jingmin");
        IPage<User> userIPage = userMapper.selectPage(new Page<>(3,2), wrapper);
        System.out.println(userIPage);
    }

    @Test
    public void selectPage1() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("account","password")
                .eq("account", "jingmin");
        IPage<User> userIPage = userMapper.selectPage(new Page<>(3,2), wrapper);
        System.out.println(userIPage);

    }



    @Test
    public void insert() {
        User user = new User();
        user.setAccount("jingmin");
        user.setPassword("123456");
        System.out.println(user.getUserId());
        userMapper.insert(user);
        System.out.println(user.getUserId());

    }

}
