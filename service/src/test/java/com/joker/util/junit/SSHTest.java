package com.joker.util.junit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.joker.util.SSHUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SSHTest {

    @Test
    public void execute() {
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            SSHUtil.execute(session, "date \"+%Y-%m-%d %H:%M:%S\"");
            SSHUtil.execute(session, "git --version");
            SSHUtil.execute(session, "java -version");
        } catch (JSchException e) {
            log.info(e.getMessage(), e);
        } finally {
            SSHUtil.close(session);
        }
    }

    @Test
    public void upload() {
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            SSHUtil.upload(sftp, "C:\\Download", "/xq/test");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SSHUtil.close(session);
        }
    }

    @Test
    public void download() {
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            SSHUtil.download(sftp, "C:/Download", "/xq/Download");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SSHUtil.close(session);
        }
    }

    @Test
    public void delete() {
        Session session = null;
        try {
            session = SSHUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            SSHUtil.delete(session,"/xq/Download/aaaa");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SSHUtil.close(session);
        }
    }

}
