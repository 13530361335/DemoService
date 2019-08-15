package com.joker.test.junit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.joker.util.LogUtil;
import com.joker.util.SshUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

@Slf4j
public class SSHTest {

    @Test
    public void execute() {
        Session session = null;
        String logPath = "/usr/task/1.log";
        new File(logPath).delete();
        LogUtil logUtil = LogUtil.getInstance(logPath);
        try {
            // 建立连接，识别机器类型
//            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            session = SshUtil.connect("127.0.0.1", 22, "administrator", "jing2019");
            boolean isWin = SshUtil.isWin(session);
            String osType = isWin ? "Windows" : "Linux";
            log.info("登陆成功，机器类型: " + osType);
            logUtil.info("登陆成功，机器类型: " + osType);

            log.info("清理进程开始");
            logUtil.info("清理进程开始");

            if (isWin) {
                String killPython = "taskkill /f /t /im python.exe";
                log.info(killPython);
                logUtil.info(killPython);
                SshUtil.execute(session, killPython);

                String killGit = "taskkill /f /t /im git.exe";
                log.info(killGit);
                logUtil.info(killGit);
                SshUtil.execute(session, killGit);
            }

            log.info("清理进程完成");
            logUtil.info("清理进程完成");
        } catch (JSchException e) {
            log.info(e.getMessage(), e);
            logUtil.info("登陆失败");
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
//            session = SshUtil.connect("127.0.0.1", 22, "administrator", "jing2019");
            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            boolean delete = SshUtil.delete(session, "C:\\Download\\123");
            log.info("删除结果: {}", delete);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

    @Test
    public void isWin() {
        Session session = null;
        try {
//            session = SshUtil.connect("127.0.0.1", 22, "administrator", "jing2019");
            session = SshUtil.connect("47.105.168.197", 22, "root", "Lxq931129");
            boolean win = SshUtil.isWin(session);
            System.out.println(win);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            SshUtil.close(session);
        }
    }

}
