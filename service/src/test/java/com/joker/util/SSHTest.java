package com.joker.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

@Slf4j
public class SSHTest {

    @Test
    public void execute() {
        try {
            Session session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            SSHUtil.execute(session, "date");
            SSHUtil.close(session);
        } catch (JSchException e) {
            log.info(e.getMessage(),e);
        }
    }

    @Test
    public void upload() {
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            SSHUtil.upload(sftp, "C:\\Download", "/xq/test");
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

    @Test
    public void download() {
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println(sftp.getHome());
            SSHUtil.download(sftp, "C:/Download", "/xq/Download");
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

    @Test
    public void getCanonicalPath() {
        File directory = new File("C:/a\\d\\\\/");//设定为当前文件夹
        log.info(directory.getAbsolutePath());//获取标准的路径
    }


}
