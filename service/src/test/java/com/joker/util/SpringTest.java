package com.joker.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {

    @Autowired
    private FTPUtil ftpUtil;

    @Test
    public void upload(){
        ftpUtil.upload("/a/bc/f/g/i/","tt.jar","C:\\Softwares\\FTP\\down-ftp.jar");
    }

    @Test
    public void download(){
        ftpUtil.download("/a/bc/f/g/i/","tt.jar","C:\\Softwares\\FTP\\d.jar");
    }

    @Test
    public void del(){
        ftpUtil.delete("/a/bc/f/g","i");
    }


}
