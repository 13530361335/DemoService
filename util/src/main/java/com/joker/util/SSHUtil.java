package com.joker.util;

import com.jcraft.jsch.*;
import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;

public class SSHUtil {
    private final static Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    public static Session connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");          // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
        session.setConfig("PreferredAuthentications", "password"); // 跳过手动输入
        session.setPassword(password);
        session.connect(5000);                     // 5秒连接超时
        return session;
    }

    private static void close(Session session, ChannelExec channelExec) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        if (channelExec != null && channelExec.isConnected()) {
            channelExec.disconnect();
        }
    }

    public static String execute(String host, int port, String username, String password, String command) throws JSchException {
        long startTime = System.currentTimeMillis();
        Session session = null;
        ChannelExec channelExec = null;
        try {
            session = connect(host, port, username, password);
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
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            return null;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("运行时间:{}", endTime - startTime);
            close(session, channelExec);
        }
    }

    /**
     * sftp上传文件（夹）
     *
     * @param sftp
     * @param localPath
     * @param remotePath
     * @throws Exception
     */
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

            String fileSize = FileSizeUtil.formetFileSize(localFile.length());
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

    /**
     * sftp下载文件（夹）
     *
     * @param directory 下载文件上级目录
     * @param srcFile   下载文件完全路径
     * @param saveFile  保存文件路径
     * @param sftp      ChannelSftp
     * @throws UnsupportedEncodingException
     */
    public static void download(ChannelSftp sftp, String localPath, String directory, String srcFile) throws UnsupportedEncodingException, SftpException {
        Vector files = sftp.ls(srcFile);
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdir();
        }
        if (srcFile.indexOf(".") > -1) {
            try {
                sftp.get(srcFile, localPath);
            } catch (SftpException e) {
                System.out.println("ChannelSftp sftp下载文件发生错误," + e);
            }
        } else {
            //文件夹(路径)
            for (Iterator iterator = files.iterator(); iterator.hasNext(); ) {
                ChannelSftp.LsEntry obj = (ChannelSftp.LsEntry) iterator.next();
                String filename = new String(obj.getFilename().getBytes(), "UTF-8");
                if (!(filename.indexOf(".") > -1)) {
                    directory = directory + System.getProperty("file.separator") + filename;
                    srcFile = directory;
                    localPath = localPath + System.getProperty("file.separator") + filename;
                } else {
                    //扫描到文件名为".."这样的直接跳过
                    String[] arr = filename.split("\\.");
                    if ((arr.length > 0) && (arr[0].length() > 0)) {
                        srcFile = directory + System.getProperty("file.separator") + filename;
                    } else {
                        continue;
                    }
                }
                download(sftp, localPath, directory, srcFile);
            }
        }
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
