package com.joker.controller;

import com.joker.common.Result;
import com.joker.service.Third.ThirdService;
import com.joker.util.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ThirdService thirdService;

    @GetMapping("download")
    public void download() throws IOException {
        String fileName = "ideaIU-2018.3.4.exe";
        HttpUtil.setDownloadFileName(fileName);
        InputStream in = new FileInputStream("D:/Downloads/ideaIU-2018.3.4.exe");
        OutputStream out = HttpUtil.getResponse().getOutputStream();
        IOUtils.copy(in,out);
    }

    @GetMapping("third")
    public Result third() {
        return thirdService.doGet();
    }

}
