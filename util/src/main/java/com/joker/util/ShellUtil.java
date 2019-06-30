package com.joker.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ShellUtil {

    private final static String DEFAULT_CHARSET = System.getProperty("os.name").startsWith("Windows") ? "GBK" : "UTF-8";

    public static void execute(String... command) throws IOException {
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), DEFAULT_CHARSET))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
        }
    }

}
