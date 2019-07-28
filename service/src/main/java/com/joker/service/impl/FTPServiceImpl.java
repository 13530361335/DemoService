package com.joker.service.impl;

import java.io.*;

import com.joker.service.FTPService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FTPServiceImpl implements FTPService {

    private final static String CONTROL_ENCODING = "UTF-8";
    private final static int CONNECT_TIME_OUT = 10 * 1000;

    @Value("${spring.ftp.host}")
    private String host = "127.0.0.1";
    @Value("${spring.ftp.port}")
    private int port = 21;
    @Value("${spring.ftp.username}")
    private String username = "ftpuser";
    @Value("${spring.ftp.password}")
    private String password = "ftpuser";

    /**
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param out
     * @return
     */
    @Override
    public boolean downloadFile(String remoteDir, String fileName, OutputStream out) {
        FTPClient ftpClient = null;
        try {
            ftpClient = connect();

            // 切换目录
            if (!ftpClient.changeWorkingDirectory(remoteDir)) {
                log.error("change working directory fail:  {}", remoteDir);
                return false;
            }

            // 判断目录是否存在文件
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles.length == 0) {
                log.error("working directory is empty");
                return false;
            }

            // 遍历目录文件，根据文件名匹配需要下载的文件
            boolean isExist = false;
            for (FTPFile file : ftpFiles) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    isExist = true;
                    // 执行下载方法
                    if (ftpClient.retrieveFile(file.getName(), out)) {
                        log.info("download success");
                        return true;
                    }
                }
            }

            if (!isExist) {
                log.error("file not found: {}", fileName);
            } else {
                log.error("download fail");
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            disConnect(ftpClient);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param localPath 保存的全路径
     * @return
     */
    @Override
    public boolean downloadFile(String remoteDir, String fileName, String localPath) {
        try {
            // 下载目录不存在则创建
            File localFile = new File(localPath);
            File parentFile = localFile.getParentFile();
            if (!parentFile.isDirectory()) {
                boolean mkdirs = parentFile.mkdirs();
                if (!mkdirs) {
                    log.error("mkdirs fail: {}", parentFile.getPath());
                    return false;
                } else {
                    log.info("mkdirs success: {}", parentFile.getPath());
                }
            }
            OutputStream out = new FileOutputStream(localPath);
            return downloadFile(remoteDir, fileName, out);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
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
    @Override
    public boolean uploadFile(String remoteDir, String fileName, InputStream in) {
        FTPClient ftpClient = null;
        try {
            ftpClient = this.connect();

            // 切换目录
            changeWorkingDirectory(ftpClient, remoteDir);

            // 执行上传方法
            return ftpClient.storeFile(fileName, in);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return false;
        } finally {
            disConnect(ftpClient);
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 上传文件
     *
     * @param remoteDir FTP的文件夹
     * @param fileName  FTP的文件名
     * @param localPath 上传的全路径
     * @return
     */
    @Override
    public boolean uploadFile(String remoteDir, String fileName, String localPath) {
        log.info("upload  {}  >>>  {}", localPath, remoteDir + "/" + fileName);
        try {
            InputStream in = new FileInputStream(localPath);
            return uploadFile(remoteDir, fileName, in);
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
    @Override
    public boolean deleteFile(String remoteDir, String fileName) {
        log.info("del  {}", remoteDir + "/" + fileName);
        FTPClient ftpClient = null;
        try {
            ftpClient = connect();
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
    private FTPClient connect() throws IOException {
        FTPClient ftpClient = new FTPClient();
        // 设置字符集
        ftpClient.setControlEncoding(CONTROL_ENCODING);
        // 设置连接超时时间
        ftpClient.setConnectTimeout(CONNECT_TIME_OUT);
        // 设置为被动模式
        ftpClient.enterLocalPassiveMode();

        // 建立连接
        ftpClient.connect(host, port);
        ftpClient.login(username, password);
        return ftpClient;
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
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 切换工作目录
     *
     * @param ftpClient
     * @param path
     */
    private static void changeWorkingDirectory(FTPClient ftpClient, String path) throws IOException {
        String[] dirs = path.split("/");
        StringBuffer current = new StringBuffer();
        for (int i = 1; i < dirs.length; i++) {
            current.append("/").append(dirs[i]);
            if (!ftpClient.changeWorkingDirectory(current.toString())) {
                ftpClient.makeDirectory(current.toString());
                ftpClient.changeWorkingDirectory(current.toString());
                log.info("create and change working directory to: {}", current);
            } else {
                log.info("change working directory to: {}", current);
            }
        }
    }

}
