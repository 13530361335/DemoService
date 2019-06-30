package com.joker.util;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP帮助类
 *
 * @author wangbailin
 */
public class SFTPUtil {


    /**
     * 连接sftp服务器
     *
     * @param host     远程主机ip地址
     * @param port     sftp连接端口，null 时为默认端口
     * @param user     用户名
     * @param password 密码
     * @return
     * @throws JSchException
     */
    public static Session connect(String host, Integer port, String user, String password) throws JSchException {
        Session session;
        try {
            JSch jsch = new JSch();
            if (port != null) {
                session = jsch.getSession(user, host, port.intValue());
            } else {
                session = jsch.getSession(user, host);
            }
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");    // 设置第一次登陆的时候提示，可选值:(ask | yes | no)
            session.connect(5000);    // 5秒连接超时
        } catch (JSchException e) {
            System.out.println("远程连接失败");
            throw e;
        }
        return session;
    }

    /**
     * sftp上传文件（夹）
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
                LsEntry obj = (LsEntry) iterator.next();
                String filename = new String(obj.getFilename().getBytes(), "UTF-8");
                if (!(filename.indexOf(".") > -1)) {
                    directory = normalize(directory + System.getProperty("file.separator") + filename);
                    srcFile = directory;
                    localPath = normalize(localPath + System.getProperty("file.separator") + filename);
                } else {
                    //扫描到文件名为".."这样的直接跳过  
                    String[] arr = filename.split("\\.");
                    if ((arr.length > 0) && (arr[0].length() > 0)) {
                        srcFile = normalize(directory + System.getProperty("file.separator") + filename);
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

    public static String normalize(String path) {
        return path;
    }
}  