package com.joker.test.junit;

import com.joker.test.FileUtil;
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

    @Test
    public void delete() {
        FileUtil.delete("C:\\Download\\bak2");
    }


    @Test
    public void unZipFile() {
        FileUtil.unZipFile("C:\\Download\\bak.zip","C:\\Download\\bak2");
    }

    @Test
    public void downLoad() {
        FileUtil.downLoad("https://down.360safe.com/instbeta.exe","C:\\Download\\");
    }

}
