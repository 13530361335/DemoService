package com.joker.util.spring;

import com.joker.service.FTPService;
import com.joker.util.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FTPTest extends SpringBootBaseTest {

    @Autowired
    private FTPService ftpService;

    @Test
    public void upload(){
        ftpService.upload("/Script","sftp.jar","C:\\Softwares\\Script\\sftp.jar");
    }

    @Test
    public void download(){
        ftpService.download("/Script","/Script","C:\\Softwares\\Script");
    }

    @Test
    public void del(){
        ftpService.delete("/a/bc/f/g","i");
    }


}
