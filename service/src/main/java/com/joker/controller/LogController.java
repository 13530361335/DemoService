package com.joker.controller;

import com.joker.common.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description: 统一常量存放
 */
@RestController
@RequestMapping("log")
public class LogController {
    @Value("${logging.file}")
    private String logPath;

    @GetMapping("download")
    public void download() throws IOException {
        String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
        HttpUtil.setFileHeader(fileName);
        InputStream in = new FileInputStream(logPath);
        OutputStream out = HttpUtil.getResponse().getOutputStream();
        IOUtils.copy(in, out);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
    }

}
