package com.joker.controller;

import com.joker.common.Result;
import com.joker.dao.UserInfoMapper;
import com.joker.entity.UserInfo;
import com.joker.third.ThirdService;
import com.joker.util.FileUtil;
import com.joker.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController("test")
public class TestController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ThirdService thirdService;

    @GetMapping("sql")
    public Result<UserInfo> sql() {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey("1");
        return new Result<>(userInfo);
    }

    @GetMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = "ideaIU-2018.3.4.exe";
        HttpUtil.setDownHeader(request, response, fileName);
        try (InputStream in = new FileInputStream("D:/Downloads/ideaIU-2018.3.4.exe"); OutputStream out = response.getOutputStream()) {
            FileUtil.transport(in, out);
        }
    }

    @GetMapping("third")
    public Result third() {
        return thirdService.doGet();
    }

}
