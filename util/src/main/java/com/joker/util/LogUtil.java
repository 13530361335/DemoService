package com.joker.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Joker Jing
 * @date: 2019/8/11
 */
@Slf4j
public class LogUtil {

    public static void info(String logPath, String message) {
        log(logPath, message, "INFO");
    }

    public static void debug(String logPath, String message) {
        log(logPath, message, "DEBUG");
    }

    public static void warn(String logPath, String message) {
        log(logPath, message, "WARN");
    }

    public static void error(String logPath, String message) {
        log(logPath, message, "ERROR");
    }

    private static void log(String logPath, String message, String level) {
        File logFile = new File(logPath);
        File logDir = logFile.getParentFile();
        if (!logDir.isDirectory()) {
            logDir.mkdirs();
        }
        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(logFile, true);
            String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            pw = new PrintWriter(fw);
            pw.println(dateTime + "  " + level + "  " + message);
            pw.flush();
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(pw);
        }
    }

}
