package com.joker.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: Joker Jing
 * @date: 2019/8/11
 */
@Slf4j
public class LogUtil {

    private final File logFile;

    public static LogUtil getInstance(String logPath) {
        return new LogUtil(logPath);
    }

    private LogUtil(String logPath) {
        this.logFile = new File(logPath);
    }

    public void info(String message) {
        log(message, "INFO");
    }

    public void debug(String message) {
        log(message, "DEBUG");
    }

    public void warn(String message) {
        log(message, "WARN");
    }

    public void error(String message) {
        log(message, "ERROR");
    }

    private void log(String message, String level) {
        // 日志格式
        String pattern = "{0}  {1}  {2}";
        String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        String line = MessageFormat.format(pattern, dateTime, level, message);
        // FileUtils工具写入日志，日志路径自动创建
        try {
            FileUtils.writeLines(logFile, "utf-8", Arrays.asList(line), true);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        System.out.println(format);
    }

}
