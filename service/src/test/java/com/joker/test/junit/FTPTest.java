package com.joker.test.junit;

import com.joker.service.impl.FTPServiceImpl;
import org.junit.Test;

public class FTPTest{

    @Test
    public void upload(){
        new FTPServiceImpl().uploadFile("/Script","sftp.jar","C:\\Softwares\\Script\\sftp.jar");
    }

    @Test
    public void download(){
        new FTPServiceImpl().downloadFile("/Script","sftp.jar","C:\\Softwares\\abc\\sftp.jar");
    }

    @Test
    public void del(){
        new FTPServiceImpl().deleteFile("/Script","sftp.jar");
    }


}
