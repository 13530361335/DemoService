package com.joker.test.junit;

import com.joker.service.impl.FtpServiceImpl;
import org.junit.Test;

public class FTPTest {

    @Test public void upload() {
        new FtpServiceImpl().uploadFile("/Script", "sftp.jar", "C:\\Softwares\\Script\\sftp.jar");
    }

    @Test public void download() {
        new FtpServiceImpl().downloadFile("/Script", "sftp.jar", "C:\\Softwares\\abc\\sftp.jar");
    }

    @Test public void del() {
        new FtpServiceImpl().deleteFile("/Script", "sftp.jar");
    }

}
