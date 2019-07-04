package com.joker.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;

import java.io.File;

public class SSHTest {

    @Test
    public void execute() {
        try {
            Session session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            SSHUtil.execute(session, "date");
            SSHUtil.disconnect(session);
            SSHUtil.execute(session, "java -version");
        } catch (JSchException e) {
            System.out.println(e);
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
            SSHUtil.upload(sftp, "C:\\Download\\run.log", "/xq/test");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("备份文件到远程主机发生错误");
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

    @Test
    public void downloadFile() {
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            SSHUtil.download(sftp, "C:/Download/aaa/b/1.log", "/xq/Download/1.log");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

    @Test
    public void downloadDir() {
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            SSHUtil.downloadDir(sftp, "C:\\Download\\zzzz/", "/xq/Download/aaaa/");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

    @Test
    public void getCanonicalPath() {
        File directory = new File("C:/a\\d\\\\/");//设定为当前文件夹
        System.out.println(directory.getAbsolutePath());//获取标准的路径
    }


}
