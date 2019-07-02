package com.joker.controller;

import com.joker.util.FileUtil;
import com.joker.util.HttpUtil;
import com.joker.util.IOUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by Joker on 2019/6/30
 */
@RestController
@RequestMapping("log")
public class LogController {
    @Value("${logging.file}")
    private String logPath;

    @GetMapping("download")
    public void download() throws IOException {
        String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
        HttpServletRequest request = HttpUtil.getRequest();
        HttpServletResponse response = HttpUtil.getResponse();
        HttpUtil.setDownHeader(request, response, fileName);
        try (InputStream in = new FileInputStream(logPath); OutputStream out = response.getOutputStream()) {
            IOUtil.transport(in, out);
        }
    }

}
