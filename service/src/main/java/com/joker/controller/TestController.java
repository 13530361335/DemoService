package com.joker.controller;

import com.joker.common.Result;
import com.joker.dao.UserInfoMapper;
import com.joker.entity.UserInfo;
import com.joker.util.ftp.FtpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


@RestController("test")
public class TestController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private FtpTemplate ftpTemplate;

    @GetMapping("sql")
    public UserInfo sql(){
        return userInfoMapper.selectByPrimaryKey("1");
    }

    @GetMapping("down")
    public Result down() throws FileNotFoundException {
        ftpTemplate.upload("test","1.xlsx",new FileInputStream("D:/Temp/1.xlsx"));
        return new Result();
    }
}
