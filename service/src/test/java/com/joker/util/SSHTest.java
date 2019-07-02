package com.joker.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;


public class SSHTest {

    @Test
    public void execute() {
        try {
            SSHUtil.execute("47.105.168.197",22,"root", "Lxq931129", "ls");
        } catch (JSchException e) {
            System.out.println(e);
            System.out.println("远程机连接失败");
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
            SSHUtil.upload(sftp, "C:\\Download\\", "/xq/test/");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("备份文件到远程主机发生错误");
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
            SSHUtil.download(sftp, "C:\\Download\\xq", "/xq/Download", "/xq/Download");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }
}
