package com.joker.controller;

import com.joker.common.Constant;
import com.joker.common.Result;
import com.joker.service.Third.ThirdService;
import com.joker.test.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.*;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ThirdService thirdService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("third")
    public Result third() {
        return thirdService.doGet();
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

}
