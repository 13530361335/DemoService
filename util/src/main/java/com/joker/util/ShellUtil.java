package com.joker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ShellUtil {

    private final static String DEFAULT_CHARSET = getDefaultCharset();

    private final static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    public static void execute(String... command) throws IOException {
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), DEFAULT_CHARSET))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        }
        process.destroy();
    }

    public static String getDefaultCharset() {
        return System.getProperty("os.name").toUpperCase().startsWith("WIN") ? "GBK" : "UTF-8";
    }

}
