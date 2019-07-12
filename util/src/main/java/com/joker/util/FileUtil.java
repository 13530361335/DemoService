package com.joker.util;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Enumeration;

/**
 * created by Joker on 2019/7/2
 * 1.压缩
 * 2.解压
 * 3.加密压缩
 * 4.解密解压
 * 5.文件删除（含文件夹）
 * 6.文件写入内容
 * 7.文件读取内容
 */
public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
     *
     * @param zipPath    需要解压的ZIP文件
     * @param targetPath 目标路径
     */
    public static boolean unZipFile(String zipPath, String targetPath) {
        targetPath = new File(targetPath).getAbsolutePath() + File.separator;
        ZipFile zipFile = null;
        boolean flag;
        try {
            zipFile = new ZipFile(zipPath);
            Enumeration<ZipEntry> enums = zipFile.getEntries();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                String entryName = entry.getName();
                String unFile = targetPath + entryName;
                boolean isDirectory = entry.isDirectory();
                File unDir = isDirectory ? new File(unFile) : new File(unFile).getParentFile();
                if (!unDir.exists()) {
                    flag = unDir.mkdirs();
                    if (!flag) {
                        logger.warn("create unDir failed");
                    }
                }
                if (isDirectory) {
                    continue;
                }
                InputStream in = zipFile.getInputStream(entry);
                OutputStream out = new FileOutputStream(unFile);
                IOUtils.copy(in,out);
                in.close();
                out.close();
            }
            logger.info("unZipFile success");
            return true;
        } catch (IOException e) {
            logger.error("unZipFile error", e);
            return false;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 路径
     */
    public static void delete(String path) {
        File file = new File(path).getAbsoluteFile();
        if (!file.exists()) {
            logger.warn("file is not exists:{}", path);
        }
        if (file.isDirectory()) {// 如果是目录，先递归删除
            String[] list = file.list();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    delete(path + File.separator + list[i]);// 先删除目录下的文件
                }
            }
        }
        file.delete();
    }

    /**
     * 下载文件
     *
     * @param url
     * @param saveDir
     */
    public static void downLoad(String url, String saveDir) {
        InputStream in = null;
        OutputStream out = null;
        String[] urls = url.split("://");
        String protocol = urls[0];
        String uri = urls[1];
        String referer = protocol + "://" + uri.substring(0, uri.indexOf("/"));
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("referer", referer);
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
            conn.connect();
            String contentDisposition = conn.getHeaderField("Content-Disposition");
            System.out.println(contentDisposition);
            String fileName = contentDisposition == null ?
                    url.substring(url.lastIndexOf("/") + 1,"url".contains("?")? url.indexOf("?"):url.length()) :
                    contentDisposition.substring(contentDisposition.indexOf('"') + 1, contentDisposition.lastIndexOf('"'));
            String contentType = conn.getContentType();
            long contentLength = conn.getContentLengthLong();
            logger.info("文件名:[" + fileName + "], 类型:[" + contentType + "], 大小:[" + formatFileSize(contentLength) + "]");

            in = conn.getInputStream();
            out = new FileOutputStream(new File(saveDir).getAbsolutePath() + File.separator + fileName);
            IOUtils.copy(in,out);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 转换文件大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (size == 0) {
            return wrongSize;
        }
        if (size < 1024) {
            fileSizeString = df.format((double) size) + " B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + " KB";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + " MB";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + " GB";
        }
        return fileSizeString;
    }

    /**
     * @param filePath
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getFileSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "file is not exists";
        }
        long size = getFileSize(file);
        return formatFileSize(size);
    }


    /**
     * 获取文件或文件夹大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.isFile()) {
            return file.length();
        }
        File files[] = file.listFiles();
        if (files == null) {
            return size;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size += getFileSize(files[i]);
            } else {
                size += files[i].length();
            }
        }
        return size;
    }


}