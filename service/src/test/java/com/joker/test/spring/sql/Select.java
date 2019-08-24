package com.joker.test.spring.sql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import com.joker.test.spring.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Joker Jing
 * @date: 2019/8/24
 */
public class Select extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        User user = userMapper.selectById(81);
        System.out.println(user);
    }

    @Test
    public void selectList() {
        QueryWrapper wrapper = new QueryWrapper<>()
                .select("user_id", "account", "password")
                // 等于
                .eq("account", "jingmin")
                // 不等于
                .ne("user_id", 1)
                // in
                .in("user_id", Arrays.asList(1, 2, 3))
                // end sql
                .last("order by user_id desc");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectPage() {
        // 需要配置分页插件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().select("account", "password").eq("account", "jingmin");
        IPage<User> userIPage = userMapper.selectPage(new Page<>(3, 2), wrapper);
        userIPage.getRecords().forEach(System.out::println);
    }

}
