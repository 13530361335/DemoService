package com.joker.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * created by Joker on 2019/7/3
 */
@Slf4j
public class FileTest {

    @Test
    public void getFilesSize() {
//        log.info(FileUtil.getFileSize("C:\\inetpub"));
        log.info(new File("/a/b").getAbsolutePath());
    }

}
