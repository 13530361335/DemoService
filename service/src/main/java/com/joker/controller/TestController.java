package com.joker.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.common.Constant;
import com.joker.common.Result;
import com.joker.controller.request.RequestPage;
import com.joker.service.third.ThirdService;
import com.joker.common.HttpUtil;
import com.joker.sql.dao.UserMapper;
import com.joker.sql.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description: 统一常量存放
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ThirdService thirdService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("third")
    public Result third() {
        return thirdService.doGet();
    }

    @GetMapping("sql")
    public Result sql(RequestPage requestPage,String account) {
        String[] columns = {"user_id","role_id","account","real_name","email"};
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select(columns)
                .eq("account", account);
        IPage<User> userPage = userMapper.selectPage(new Page<>(requestPage.getCurrent(),requestPage.getSize()), wrapper);
        return new Result(userPage);
    }

    @PostMapping("upload")
    public void upload(MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        String str = HttpUtil.imgToStr(in);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_IMAGE, "1", str);
    }

    @GetMapping("viewImage")
    public void img() throws IOException {
        HttpUtil.setImageHeader();
        String str = (String) redisTemplate.opsForHash().get(Constant.REDIS_KEY_IMAGE, "1");
        ServletOutputStream out = HttpUtil.getResponse().getOutputStream();
        HttpUtil.strToImg(str, out);
    }

    @GetMapping("testRedis")
    public void testRedis() {
//        redisTemplate.expire(Constant.REDIS_KEY_VERIFICATION_CODE, 10, TimeUnit.SECONDS);

        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE,
                RandomStringUtils.randomNumeric(6),
                RandomStringUtils.randomNumeric(6));

        redisTemplate.opsForValue().set("opsForValue", "opsForValue");

        redisTemplate.opsForList().set("opsForList",10, Arrays.asList("opsForList1","opsForList2"));

        redisTemplate.opsForSet().add("opsForSet", "opsForSet1","opsForSet2");
        redisTemplate.opsForZSet().add("opsForZSet", "opsForZSet1",10.0);
    }

}
