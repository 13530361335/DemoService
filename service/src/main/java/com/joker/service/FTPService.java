package com.joker.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by Joker on 2019/7/12
 */
public interface FTPService {

    boolean downloadFile(String remoteDir, String fileName, OutputStream out);

    boolean downloadFile(String remoteDir, String fileName, String localPath);

    boolean uploadFile(String remoteDir, String fileName, InputStream in);

    boolean uploadFile(String remoteDir, String fileName, String localPath);

    boolean deleteFile(String remoteDir, String fileName);

}
