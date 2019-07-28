package com.joker.test.spring;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import com.joker.sql.service.UserService;
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

    @Autowired
    private UserService userService;

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
                .last("order by user_id desc");    // in
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectPage() {
        // 需要配置分页插件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("account", "password")
                .eq("account", "jingmin");
        IPage<User> userIPage = userMapper.selectPage(new Page<>(3, 2), wrapper);
        userIPage.getRecords().forEach(System.out::println);
    }


    @Test
    public void insert() {
        // 新增返回主键 主键需要加注解 @TableId(type = IdType.AUTO)
        User user = new User();
        user.setAccount("jingmin");
        user.setPassword("123456");
        System.out.println(user.getUserId());
        userMapper.insert(user);
        System.out.println(user.getUserId());
    }

    @Test
    public void insert1() {
        userService.saveBatch(Arrays.asList(
                new User(null, null, "xu", "123", "1", "1", "1"),
                new User(null, null, "xu", "123", "1", "1", "1"),
                new User(null, null, "xu", "123", "1", "1", "1"),
                new User(null, null, "jingmin", "123", "1", "1", "1")
        ));
    }

}
