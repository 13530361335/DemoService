package com.joker.util.spring;

import com.joker.util.FTPUtil;
import com.joker.util.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FTPTest extends SpringBootBaseTest {

    @Autowired
    private FTPUtil ftpUtil;

    @Test
    public void upload(){
        ftpUtil.upload("/Script","sftp.jar","C:\\Softwares\\Script\\sftp.jar");
    }

    @Test
    public void download(){
        ftpUtil.download("/Script","/Script","C:\\Softwares\\Script");
    }

    @Test
    public void del(){
        ftpUtil.delete("/a/bc/f/g","i");
    }


}
