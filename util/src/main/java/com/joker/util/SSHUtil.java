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

    public static void upload(ChannelSftp sftp, String localPath, String remoteDir) throws SftpException, FileNotFoundException {
        localPath = new File(localPath).getAbsolutePath();
        File localFile = new File(localPath);
        try {
            sftp.cd(remoteDir);
        } catch (SftpException e) {
            sftp.mkdir(remoteDir);
        }
        if (localFile.isFile()) {
            sftp.cd(remoteDir);
            InputStream in = new FileInputStream(localFile);
            sftp.put(in, localFile.getName());
        } else {
            File[] files = localFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    upload(sftp, file.getPath(), remoteDir + "/" + file.getName());
                } else {
                    upload(sftp, file.getPath(), remoteDir);
                }
            }
        }
    }

    // TODO 判断远程机器类型获取标准远程路径
    public static boolean download(ChannelSftp sftp, String localDir, String remotePath) {
        boolean flag = false;
        localDir = new File(localDir).getAbsolutePath();
        try {
            File saveDir = new File(localDir);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            Vector<LsEntry> files = sftp.ls(remotePath);
            if (files.size() == 1) {  // 下载文件
                LsEntry remoteFile = files.get(0);
                String fileName = remoteFile.getFilename();
                String savePath = localDir + File.separator + fileName;
                sftp.get(remotePath, savePath);
            } else if (files.size() > 2) {  // 下载目录
                for (LsEntry remoteFile : files) {
                    String fileName = remoteFile.getFilename();
                    String remoteFilePath = remotePath + "/" + fileName;
                    String savePath = localDir + File.separator + fileName;
                    SftpATTRS attrs = remoteFile.getAttrs();
                    if (!attrs.isDir()) {  // 文件
                        sftp.get(remoteFilePath, savePath);
                    } else if (!".".equals(fileName) && !"..".equals(fileName)) {  // 文件夹
                        download(sftp, savePath, remoteFilePath);
                    }
                }
            }
            flag = true;
        } catch (SftpException e) {
            log.info(e.getMessage(), e);
        }
        return flag;
    }

}
