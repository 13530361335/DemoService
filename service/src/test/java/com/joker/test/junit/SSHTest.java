package com.joker.test.junit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.joker.util.SshUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SSHTest {

    @Test
    public void execute() {
        Session session = null;
        try {
            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            boolean execute1 = SshUtil.execute(session, "pwd");
            log.info("1:{}",execute1);
            boolean execute2 = SshUtil.execute(session, "java -version");
            log.info("2:{}",execute2);
            boolean execute3 = SshUtil.execute(session, "git --version");
            log.info("3:{}",execute3);
            boolean execute4 = SshUtil.execute(session, "date \"+%Y-%m-%d %H:%M:%S\"");
            log.info("4:{}",execute4);
        } catch (JSchException e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

    @Test
    public void upload() {
        Session session = null;
        try {
            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            SshUtil.upload(sftp, "C:\\Download", "/xq/test");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

    @Test
    public void download() {
        Session session = null;
        try {
            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            SshUtil.download(sftp, "C:/Download/xq", "/xq");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

    @Test
    public void delete() {
        Session session = null;
        try {
            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            System.out.println(SshUtil.delete(session, "/xq/test"));
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

    @Test
    public void test() {
        Session session = null;
        try {
//            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            session = SshUtil.connect("127.0.0.1", 22, "administrator", "jm19930913.");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            log.info(sftp.getHome());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

}
