package com.joker.util.junit;

import com.joker.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * created by Joker on 2019/7/3
 */
@Slf4j
public class FileTest {

    @Test
    public void getFilesSize() {
        log.info(FileUtil.getFileSize("C:\\inetpub"));
    }

}
