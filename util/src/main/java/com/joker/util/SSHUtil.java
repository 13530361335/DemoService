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

    public static void disconnect(Session session) {
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

    public static void upload(ChannelSftp sftp, String localPath, String remotePath) throws Exception {
        File localFile = new File(localPath);
        try {
            sftp.cd(remotePath);
        } catch (SftpException e) {
            sftp.mkdir(remotePath);
        }
        if (localFile.isFile()) {
            long start = System.currentTimeMillis();
            sftp.cd(remotePath);
            InputStream in = new FileInputStream(localFile);
            sftp.put(in, new String(localFile.getName().getBytes(), "UTF-8"));
            // 控制台打印
            long end = System.currentTimeMillis();
            String fileName = localFile.getName();
            while (getStrLength(fileName) < 40) {
                fileName += " ";
            }

            String target = remotePath;
            while (getStrLength(target) < 40) {
                target += " ";
            }

            String fileSize = FileUtil.formatFileSize(localFile.length());
            while (fileSize.length() < 9) {
                fileSize += " ";
            }

            String time = (end - start) + "ms";
            while (time.length() < 8) {
                time += " ";
            }
            String print = "*fileName* >>> *target* *fileSize* *time*"
                    .replace("*fileName*", fileName)
                    .replace("*target*", target)
                    .replace("*fileSize*", fileSize)
                    .replace("*time*", time);
            System.out.println(print);
        } else {
            File[] files = localFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    upload(sftp, file.getPath(), remotePath + "/" + file.getName());
                } else {
                    upload(sftp, file.getPath(), remotePath);
                }
            }
        }
    }

    public static boolean download(ChannelSftp sftp, String savePath, String remotePath) {
        boolean flag = false;
        try {
            savePath = new File(savePath).getAbsolutePath();
            File saveDir = new File(savePath).getParentFile();
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            sftp.get(remotePath, savePath);
            flag = true;
        } catch (SftpException e) {
            log.info(e.getMessage(), e);
        }
        log.info("{} 下载{}", remotePath, flag ? "成功" : "失败");
        return flag;
    }

    public static boolean downloadDir(ChannelSftp sftp, String saveDir, String remoteDir) {
        boolean flag = false;
        saveDir = new File(saveDir).getAbsolutePath();
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            Vector<LsEntry> files = sftp.ls(remoteDir);
            for (LsEntry remoteFile : files) {
                String remoteFileName = remoteFile.getFilename();
                String remoteFilePath = remoteDir + "/" + remoteFileName;
                String savePath = saveDir + File.separator + remoteFileName;
                SftpATTRS attrs = remoteFile.getAttrs();
                if (!attrs.isDir()) {
                    download(sftp, savePath, remoteFilePath);
                } else if (!".".equals(remoteFileName) && !"..".equals(remoteFileName)) {
                    downloadDir(sftp, savePath, remoteFilePath);
                }
            }
            flag = true;
        } catch (SftpException e) {
            log.info(e.getMessage(), e);
        }
        return flag;
    }


    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

}
