package com.joker.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by Joker on 2019/7/12
 */
public interface FTPService {

    boolean download(String remoteDir, String fileName, OutputStream out);

    boolean download(String remoteDir, String fileName, String localPath);

    boolean upload(String remoteDir, String fileName, InputStream in);

    boolean upload(String remoteDir, String fileName, String localPath);

    boolean delete(String remoteDir, String fileName);

}
