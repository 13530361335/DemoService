package com.joker.test.util;

import com.joker.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Joker Jing
 * @date: 2019/7/3
 */
@Slf4j
public class FileTest {

    /**
     * 压缩多个目录
     * @throws Exception
     */
    @Test
    public void zip1() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("C:\\log");
        list.add("C:\\Download\\run.log.2019-07-06.0.gz");
        String[] paths = list.toArray(new String[list.size()]);
        FileUtil.zip("C:\\Code\\test1.zip", paths);
    }

    /**
     * 压缩一个目录(不带自身目录)
     * @throws Exception
     */
    @Test
    public void zip2() throws Exception {
        File dir = new File("C:\\Download");
        File[] files = dir.listFiles();
        List<String> list = new ArrayList<>();
        if(files == null){
            return;
        }
        for (File file : files) {
            list.add(file.getPath());
        }
        String[] paths = list.toArray(new String[list.size()]);
        FileUtil.zip("C:\\Code\\test2.zip", paths);
    }

    @Test
    public void unZip() {
        FileUtil.unZip("C:\\Download\\bak.zip", "C:\\Download\\bak2");
    }

    @Test
    public void getFilesSize() {
        log.info(FileUtil.getFileSize("C:\\inetpub"));
    }

    @Test
    public void delete() {
        FileUtil.delete("C:\\Download\\bak2");
    }

    @Test
    public void downLoad() {
        FileUtil.downLoad("https://down.360safe.com/instbeta.exe", "C:\\Download\\");
    }

}
