package com.joker.controller;

import com.joker.util.FileUtil;
import com.joker.util.HttpUtil;
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

    @GetMapping("view")
    public void view(HttpServletResponse response) {
        BufferedReader br = null;
        PrintWriter pw = null;
        response.setContentType("text/html;charset=utf-8");
        try {
            FileInputStream fis = new FileInputStream(logPath);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            pw = response.getWriter();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.lastIndexOf("---") < 0) {
                    pw.println(line + "<br>");
                }
                pw.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.close();
        }
        // 防止路径乱码   如果utf-8 乱码  改GBK     eclipse里创建的txt  用UTF-8，在电脑上自己创建的txt  用GBK
    }

    @GetMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
        HttpUtil.setDownHeader(request, response, fileName);
        try (InputStream in = new FileInputStream(logPath); OutputStream out = response.getOutputStream()) {
            FileUtil.transport(in, out);
        }
    }

}
