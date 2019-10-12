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
 * @author Joker Jing
 * @date 2019/8/11
 */
@Slf4j
public class LogUtil {

    /**
     * 日志格式
     * 0：日志时间
     * 1：日志级别
     * 2：日志内容
     */
    private static final String LOG_PATTERN = "{0}  {1}  {2}";

    /**
     * 时间格式
     */
    private String timePattern = "yyyy-MM-dd HH:mm:ss";

    private final File logFile;

    public static LogUtil getInstance(String logPath) {
        return new LogUtil(logPath);
    }

    public LogUtil setTimePattern(String timePattern) {
        this.timePattern = timePattern;
        return this;
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
        String dateTime = DateFormatUtils.format(new Date(), timePattern);
        String line = MessageFormat.format(LOG_PATTERN, dateTime, level, message);
        // FileUtils工具写入日志，日志路径自动创建
        try {
            FileUtils.writeLines(logFile, "utf-8", Arrays.asList(line), true);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
    }

}
