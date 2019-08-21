package com.joker.controller;

import com.joker.common.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 */
@RestController
@RequestMapping("log")
public class LogController {
    @Value("${logging.file}")
    private String logPath;

    @GetMapping("download")
    public void download(@PathVariable String path) throws IOException {
        String log = StringUtils.isEmpty(path) ? logPath : path;
        File file = new File(log);
        HttpUtil.setFileHeader(file.getName());
        InputStream in = new FileInputStream(file);
        OutputStream out = HttpUtil.getResponse().getOutputStream();
        IOUtils.copy(in, out);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
    }

}
