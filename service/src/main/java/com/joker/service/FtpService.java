package com.joker.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
public interface FtpService {

    /**
     * 下载文件
     * @param remoteDir
     * @param fileName
     * @param out
     * @return
     */
    boolean downloadFile(String remoteDir, String fileName, OutputStream out);

    /**
     * 下载文件
     * @param remoteDir
     * @param fileName
     * @param localPath
     * @return
     */
    boolean downloadFile(String remoteDir, String fileName, String localPath);

    /**
     * 上传文件
     * @param remoteDir
     * @param fileName
     * @param in
     * @return
     */
    boolean uploadFile(String remoteDir, String fileName, InputStream in);

    /**
     * 上传文件
     * @param remoteDir
     * @param fileName
     * @param localPath
     * @return
     */
    boolean uploadFile(String remoteDir, String fileName, String localPath);

    /**
     * 删除文件
     * @param remoteDir
     * @param fileName
     * @return
     */
    boolean deleteFile(String remoteDir, String fileName);

}
