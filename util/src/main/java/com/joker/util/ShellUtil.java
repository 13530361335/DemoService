package com.joker.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
@Slf4j
public class ShellUtil {
    private final static String DEFAULT_CHARSET = System.getProperty("os.name").startsWith("Win") ? "GBK" : "UTF-8";

    public static void execute(String... command) {
        Process process = null;
        BufferedReader reader = null;
        try {
            process = new ProcessBuilder(command).redirectErrorStream(true).start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), DEFAULT_CHARSET));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            if (process != null && process.isAlive()) {
                process.destroy();
            }
        }
    }

}
