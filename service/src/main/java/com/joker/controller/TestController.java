package com.joker.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.common.Constant;
import com.joker.common.HelloSender;
import com.joker.common.HttpUtil;
import com.joker.common.Result;
import com.joker.common.annotation.HandlingTime;
import com.joker.controller.request.RequestPage;
import com.joker.entity.User;
import com.joker.mapper.UserMapper;
import com.joker.service.TestService;
import com.joker.service.third.ThirdService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

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
        return Result.success(userPage);
    }

    @PostMapping("upload")
    public void upload(MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        String str = HttpUtil.streamToString(in);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_IMAGE, "1", str);
    }

    @GetMapping("viewImage")
    public void img() throws IOException {
        HttpUtil.setImageHeader();
        String str = (String) redisTemplate.opsForHash().get(Constant.REDIS_KEY_IMAGE, "1");
        OutputStream out = HttpUtil.getResponse().getOutputStream();
        HttpUtil.stringToStream(str, out);
    }

    @GetMapping("testRedis")
    public void testRedis() {
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE,
                RandomStringUtils.randomNumeric(6),
                RandomStringUtils.randomNumeric(6));

        redisTemplate.opsForValue().set("opsForValue", "opsForValue");

        redisTemplate.opsForList().set("opsForList",10, Arrays.asList("opsForList1","opsForList2"));

        redisTemplate.opsForSet().add("opsForSet", "opsForSet1","opsForSet2");
        redisTemplate.opsForZSet().add("opsForZSet", "opsForZSet1",10.0);
    }

    @Autowired
    TestService testService;

    @HandlingTime
    @GetMapping("start")
    public void start(String taskId) {
        testService.start(taskId);
    }

    @GetMapping("stop")
    public void stop(String taskId) {
        testService.stop(taskId);
    }

    @Autowired
    private HelloSender helloSender;

    @GetMapping("send")
    public void send() {
        helloSender.send();
    }

}
