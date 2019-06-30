package com.joker.util;

import com.jcraft.jsch.*;
import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SSHUtil {

    private final static Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    /**
     * 获取连接
     *
     * @throws JSchException
     */
    public static Session getConnection(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "password"); // 跳过手动输入
        session.setPassword(password);
        session.connect();
        return session;
    }

    /**
     * 关闭连接
     */
    public static void close(Session session, ChannelExec channelExec) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        if (channelExec != null && channelExec.isConnected()) {
            channelExec.disconnect();
        }
    }

    /**
     * 执行命令
     *
     * @throws JSchException
     * @throws IOException
     */
    public static String runCommand(String host, String username, String password, String command) throws JSchException, IOException {
        long startTime = System.currentTimeMillis();
        Session session = null;
        ChannelExec channelExec = null;
        try {
            session = getConnection(host, 22, username, password);
            @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.setErrStream(os);
            channelExec.connect();
            @Cleanup InputStream in = channelExec.getInputStream();
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line, result = null;
            int status;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue;
                logger.info(line);
                result = line;
            }
            while (true) {
                if (channelExec.isClosed()) {
                    status = channelExec.getExitStatus();
                    break;
                }
            }
            logger.info("结束行:{} >> 退出码:{}  ", result, status);
            logger.info(new String(os.toByteArray(), "gbk"));
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("运行时间:{}", endTime - startTime);
            close(session, channelExec);
        }
    }

    public static void putFile(String host, int port, String username, String password, String localPath, String localFile, String remotePath) throws JSchException, SftpException {
        long startTime = System.currentTimeMillis();
        ChannelSftp sftp = null;
        try {
            Session session = getConnection(host, 22, username, password);
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            String remoteFile;
            if (remotePath != null && remotePath.trim().length() > 0) {
                sftp.mkdir(remotePath);
                remoteFile = remotePath + "/.";
            } else {
                remoteFile = ".";
            }
            String file = (localFile == null || localFile.trim().length() == 0) ? "*" : localFile;
            if (localPath != null && localPath.trim().length() > 0) {
                file = localPath.endsWith("/") ? localPath + file : localPath + "/" + file;
            }
            sftp.put(file, remoteFile);
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("运行时间:{}", endTime - startTime);
            sftp.quit();
            sftp.disconnect();
        }
    }

}
