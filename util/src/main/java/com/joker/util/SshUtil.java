package com.joker.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.MessageFormat;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.io.IOUtils;

/**
 * @author Administrator
 * @date 2019/7/29
 */
@Slf4j
public class SshUtil {

    /**
     * 非空文件夹最小文件数
     */
    private static final int MIN_DIR_SIZE = 3;

    private static final String WINDOWS_SIGN = "Windows";

    public static final Session connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // 跳过手动输入
        session.setConfig("PreferredAuthentications", "password");
        session.setPassword(password);
        // 5秒连接超时
        session.connect(5000);
        log.info("ssh connect success, server version: {}", session.getServerVersion());
        return session;
    }

    public static void close(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public static boolean execute(Session session, String command) throws JSchException {
        return execute(session, command, null);
    }

    public static boolean execute(Session session, String command, String logPath) throws JSchException {
        ByteArrayOutputStream errorOut = new ByteArrayOutputStream();
        BufferedReader reader = null;
        LogUtil logUtil = null;
        if (logPath != null) {
            logUtil = LogUtil.getInstance(logPath);
        }
        try {
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            exec.setCommand(command);
            exec.setErrStream(errorOut);
            exec.connect();
            String charsetName = isWin(session) ? "GBK" : "UTF-8";
            reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), charsetName));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                if (logPath != null) {
                    logUtil.info(line);
                }
            }
            while (exec.isConnected()) {
                if (exec.getExitStatus() == 0) {
                    return true;
                }
            }
            String errorMessage = new String(errorOut.toByteArray(), charsetName);
            log.error(errorMessage);
            if (logPath != null) {
                logUtil.info(errorMessage);
            }
            return false;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(errorOut);
        }
    }

    public static void upload(ChannelSftp sftp, String localPath, String remoteDir) throws SftpException {
        localPath = new File(localPath).getAbsolutePath();
        // 根据home路径获取文件分隔符
        String fileSeparator = sftp.getHome().indexOf("/") == 0 ? "/" : "\\";
        File localFile = new File(localPath);
        try {
            sftp.cd(remoteDir);
        } catch (SftpException e) {
            sftp.mkdir(remoteDir);
            log.debug("create remoteDir {}", remoteDir);
        }
        if (localFile.isFile()) {
            sftp.put(localPath, remoteDir + fileSeparator + localFile.getName());
            log.debug("upload file {} >>> {}", localPath, remoteDir + fileSeparator + localFile.getName());
        } else {
            File[] files = localFile.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                String remotePath = remoteDir + fileSeparator + file.getName();
                if (!file.isDirectory()) {
                    sftp.put(file.getPath(), remotePath);
                    log.debug("upload file {} >>> {}", file.getPath(), remotePath);
                } else {
                    upload(sftp, file.getPath(), remotePath);
                }
            }
        }
    }

    public static void download(ChannelSftp sftp, String localDir, String remotePath) throws SftpException {
        localDir = new File(localDir).getAbsolutePath();
        // 根据home路径获取文件分隔符
        String fileSeparator = sftp.getHome().indexOf("/") == 0 ? "/" : "\\";
        File saveDir = new File(localDir);
        if (!saveDir.isDirectory()) {
            log.debug("mkdirs localDir {}", localDir);
            boolean mkdirs = saveDir.mkdirs();
            if (!mkdirs) {
                log.error("mkdirs {} fail", localDir);
                return;
            }
        }
        Vector<LsEntry> files = sftp.ls(remotePath);
        if (files.size() == 1) {
            sftp.get(remotePath, localDir + File.separator + files.get(0).getFilename());
            log.debug("download file {} >>> {}", remotePath, localDir + File.separator + files.get(0).getFilename());
        } else if (files.size() >= MIN_DIR_SIZE) {
            for (LsEntry file : files) {
                String fileName = file.getFilename();
                String remoteFilePath = remotePath + fileSeparator + fileName;
                String savePath = localDir + File.separator + fileName;
                if (!file.getAttrs().isDir()) {
                    sftp.get(remoteFilePath, savePath);
                    log.debug("download file {} >>> {}", remoteFilePath, savePath);
                } else if (!".".equals(fileName) && !"..".equals(fileName)) {
                    download(sftp, savePath, remoteFilePath);
                }
            }
        }
    }

    public static boolean delete(Session session, String filePath) throws JSchException {
        String command = isWin(session) ?
                MessageFormat.format("if exist {0} (del {0}\\* /s/q & rd {0} /s/q)", filePath) :
                MessageFormat.format("rm -rf {0}", filePath);
        log.info("delete command: {}", command);
        return execute(session, command);
    }

    public static boolean isWin(Session session) {
        String serverVersion = session.getServerVersion();
        return (serverVersion != null && serverVersion.contains(WINDOWS_SIGN));
    }

}
