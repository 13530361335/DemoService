package com.joker.util;

import java.io.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FTPUtil {
    private final static String controlEncoding = "UTF-8";
    private final static long keepAliveTimeOut = 0;
    private final static int connectTimeOut = 10 * 1000;
    private final static boolean passiveMode = false;
    private final static int fileType = FTPClient.BINARY_FILE_TYPE;
    private final static int bufferSize = 1024 * 1024;
    private final static int dataTimeOut = 60 * 1000;

    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port = 21;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;

    /**
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param out
     * @return
     */
    public boolean download(String remoteDir, String fileName, OutputStream out) {
        int code = 0;
        FTPClient ftpClient = null;
        try {
            ftpClient = this.connect();
            ftpClient.changeWorkingDirectory(remoteDir);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles.length == 0) {
                code = 550;
            }
            for (FTPFile file : ftpFiles) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    if (ftpClient.retrieveFile(file.getName(), out)) {
                        code = 250;
                        break;
                    } else {
                        code = 500;
                    }
                } else {
                    code = 550;
                }
            }
        } catch (Exception e) {
            code = 500;
            log.info(e.getMessage(), e);
        } finally {
            disConnect(ftpClient);
            IOUtils.closeQuietly(out);
        }
        return code == 250;
    }

    /**
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param localPath 保存的全路径
     * @return
     */
    public boolean download(String remoteDir, String fileName, String localPath) {
        log.info("download  " + remoteDir + "/" + fileName + "  >>>  " + localPath);
        try {
            // 下载目录不存在则创建
            String localDir = localPath.substring(0, localPath.lastIndexOf(File.separator));
            File dir = new File(localDir);
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }
            OutputStream out = new FileOutputStream(localPath);
            return download(remoteDir, fileName, out);
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param in
     * @return
     */
    public boolean upload(String remoteDir, String fileName, InputStream in) {
        int code = 0;
        FTPClient ftpClient = null;
        try {
            ftpClient = this.connect();
            changeDir(ftpClient, remoteDir);
            final int retryTimes = 3;
            for (int j = 0; j <= retryTimes; j++) {
                if (ftpClient.storeFile(fileName, in)) {
                    code = 250;
                    break;
                } else {
                    code = 550;
                    log.info("upload file failure:" + j);
                }
            }
        } catch (Exception e) {
            code = 500;
            log.info(e.getMessage(), e);
        } finally {
            disConnect(ftpClient);
            IOUtils.closeQuietly(in);
        }
        return code == 250;
    }

    /**
     * 上传文件
     *
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param localPath 上传的全路径
     * @return
     */
    public boolean upload(String remoteDir, String fileName, String localPath) {
        log.info("upload " + localPath + "  >>>  " + remoteDir + "/" + fileName);
        try {
            InputStream in = new FileInputStream(localPath);
            return upload(remoteDir, fileName, in);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     */
    public boolean delete(String remoteDir, String fileName) {
        log.info("del  " + remoteDir + "/" + fileName);
        FTPClient ftpClient = null;
        try {
            ftpClient = this.connect();
            ftpClient.changeWorkingDirectory(remoteDir);
            ftpClient.dele(fileName);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return false;
        } finally {
            disConnect(ftpClient);
        }
    }

    /**
     * 建立FTP连接
     *
     * @return
     */
    private FTPClient connect() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding(controlEncoding);
            ftpClient.setControlKeepAliveTimeout(keepAliveTimeOut);
            ftpClient.setConnectTimeout(connectTimeOut);
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setDataTimeout(dataTimeOut);
            ftpClient.setFileType(fileType);
            ftpClient.setBufferSize(bufferSize);
            if (passiveMode) {
                // 设置为被动模式
                ftpClient.enterLocalPassiveMode();
            }
            return ftpClient;
        } catch (IOException e) {
            log.info(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 关闭FTP连接
     *
     * @param ftpClient
     */
    private static void disConnect(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.info(e.getMessage(), e);
            }
        }
    }

    private static void changeDir(FTPClient ftpClient, String path) {
        String[] dirs = path.split("/");
        String current = "";
        for (int i = 1; i < dirs.length; i++) {
            current += "/" + dirs[i];
            try {
                if (!ftpClient.changeWorkingDirectory(current)) {
                    log.info("创建并切换FTP目录：{}", current);
                    ftpClient.makeDirectory(current);
                    ftpClient.changeWorkingDirectory(current);
                } else {
                    log.info("切换FTP目录：{}", current);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
