package com.joker.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.io.IOUtils;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
@Slf4j
public class SshUtil {

    private static final int MIN_EMPTY_SIZE = 3;

    public static Session connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // 跳过手动输入
        session.setConfig("PreferredAuthentications", "password");
        session.setPassword(password);
        // 5秒连接超时
        session.connect(5000);
        return session;
    }

    public static void close(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public static boolean execute(Session session, String command) throws JSchException {
        boolean flag = false;
        ByteArrayOutputStream errorOut = new ByteArrayOutputStream();
        BufferedReader reader = null;
        try {
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            exec.setCommand(command);
            exec.setErrStream(errorOut);
            exec.connect();
            reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            if (exec.getExitStatus() == 0) {
                flag = true;
            } else {
                log.error(new String(errorOut.toByteArray(), "utf-8"));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(errorOut);
        }
        return flag;
    }

    public static void upload(ChannelSftp sftp, String localPath, String remoteDir) throws SftpException {
        localPath = new File(localPath).getAbsolutePath();
        // 根据home路径获取文件分隔符
        String fileSeparator = sftp.getHome().indexOf("/") == 0 ? "/" : "\\";
        File localFile = new File(localPath);
        try {
            // TODO 通过异常方式判断有无远程目录，没有则创建
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
                    log.debug("upload file {} >>> {}", remotePath);
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
        } else if (files.size() >= MIN_EMPTY_SIZE) {
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

    public static void delete(Session session, String filePath) throws JSchException {
        execute(session, "rm -rf " + filePath);
    }

}
