package com.joker.util;

import com.jcraft.jsch.*;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp.LsEntry;

@Slf4j
public class SSHUtil {

    public static Session connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");          // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        session.setConfig("PreferredAuthentications", "password"); // 跳过手动输入
        session.setPassword(password);
        session.connect(5000);                     // 5秒连接超时
        return session;
    }

    public static void close(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public static boolean execute(Session session, String command) throws JSchException {
        boolean flag = false;
        try {
            @Cleanup OutputStream errOs = new ByteArrayOutputStream();
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            exec.setCommand(command);
            exec.setErrStream(errOs);
            exec.connect();
            @Cleanup InputStream in = exec.getInputStream();
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() != 0) {
                    log.info(line);
                }
            }
            if (exec.getExitStatus() == 0) {
                flag = true;
            } else {
                log.warn("错误信息 : {}", errOs.toString());
            }
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
        return flag;
    }

    public static void upload(ChannelSftp sftp, String localPath, String remoteDir) throws SftpException {
        localPath = new File(localPath).getAbsolutePath();
        String fileSeparator = sftp.pwd().indexOf("/") == 0 ? "/" : "\\";
        File localFile = new File(localPath);
        try {
            sftp.cd(remoteDir);
        } catch (SftpException e) {
            sftp.mkdir(remoteDir);
            log.debug("{} create dir {}", e.getMessage(), remoteDir);
        }
        if (localFile.isFile()) {
            sftp.put(localPath, remoteDir + fileSeparator + localFile.getName());
        } else {
            File[] files = localFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    upload(sftp, file.getPath(), remoteDir + fileSeparator + file.getName());
                } else {
                    sftp.put(file.getPath(), remoteDir + fileSeparator + file.getName());
                }
            }
        }
    }

    // TODO 判断远程机器类型获取标准远程路径
    public static void download(ChannelSftp sftp, String localDir, String remotePath) throws SftpException {
        localDir = new File(localDir).getAbsolutePath();
        File saveDir = new File(localDir);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        Vector<LsEntry> files = sftp.ls(remotePath);
        if (files.size() == 1) {  // 下载文件
            sftp.get(remotePath, localDir + File.separator + files.get(0).getFilename());
        } else if (files.size() > 2) {  // 下载目录
            for (LsEntry file : files) {
                String fileName = file.getFilename();
                String remoteFilePath = remotePath + "/" + fileName;
                String savePath = localDir + File.separator + fileName;
                SftpATTRS attrs = file.getAttrs();
                if (!attrs.isDir()) {  // 文件
                    sftp.get(remoteFilePath, savePath);
                } else if (!".".equals(fileName) && !"..".equals(fileName)) {  // 文件夹
                    download(sftp, savePath, remoteFilePath);
                }
            }
        }
    }

}
