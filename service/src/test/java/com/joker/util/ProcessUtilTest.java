package com.joker.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ProcessUtilTest {

    private final static Logger logger = LoggerFactory.getLogger(ProcessUtil.class);

    @Test
    public void execute() {
        try {
            ProcessUtil.execute("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


}
