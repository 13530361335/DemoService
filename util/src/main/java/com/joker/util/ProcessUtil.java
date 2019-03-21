package com.joker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProcessUtil {

    private final static String DEFAULT_CHARSET = System.getProperty("os.name").toUpperCase().startsWith("WIN") ? "GBK" : "UTF-8";

    private final static Logger logger = LoggerFactory.getLogger(ProcessUtil.class);

    public static void execute(String... command) throws IOException {
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), DEFAULT_CHARSET))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        }
    }

}
