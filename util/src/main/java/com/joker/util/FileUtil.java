package com.joker.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;

/**
 * 文件操作工具类
 */
public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
     *
     * @param zipFileName 需要解压的ZIP文件
     * @param targetPath  目标路径
     */
    public static boolean unZipFile(String zipFileName, String targetPath) {
        logger.info("unZipFile start");
        String targetDir = targetPath.endsWith(File.separator) ? targetPath : targetPath + File.separator;
        ZipFile zipFile = null;
        ZipEntry entry;
        String entryName, unFile;
        byte[] bytes = new byte[4096];
        int len;
        boolean flag;
        try {
            zipFile = new ZipFile(zipFileName);
            Enumeration<ZipEntry> enums = zipFile.getEntries();
            while (enums.hasMoreElements()) {
                entry = enums.nextElement();
                entryName = entry.getName();
                unFile = targetDir + entryName;
                boolean isDirectory = entry.isDirectory();
                File dir = isDirectory ? new File(unFile) : new File(unFile).getParentFile();
                if (!dir.exists()) {
                    flag = dir.mkdirs();
                    if (!flag) {
                        logger.warn("create descFileDir failed");
                    }
                }
                if (isDirectory) {
                    continue;
                }
                try (InputStream in = zipFile.getInputStream(entry); OutputStream out = new FileOutputStream(new File(unFile))) {
                    transport(in, out);
                }
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
        File file = new File(path);
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
        boolean flag = file.delete();
        if (!flag) {
            logger.error("delete file error:{}", path);
        }
    }

    /**
     * 下载文件
     *
     * @param url
     * @param savePath
     */
    public static void downLoad(String url, String savePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("referer", getReferer(url));
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
            conn.connect();

            String contentDisposition = conn.getHeaderField("Content-Disposition");
            String fileName = contentDisposition == null ? url.substring(url.lastIndexOf("/") + 1) :
                    contentDisposition.substring(contentDisposition.indexOf('"') + 1, contentDisposition.lastIndexOf('"'));
            String contentType = conn.getContentType();
            long contentLength = conn.getContentLengthLong();
            logger.info("文件名:[" + fileName + "], 类型:[" + contentType + "], 大小:[" + contentLength + "]");

            in = conn.getInputStream();
            out = new FileOutputStream(new File((savePath + fileName)));
            transport(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private static String getReferer(String url) {
        String[] urls = url.split("://");
        String protocol = urls[0];
        String uri = urls[1];
        uri = uri.substring(0, uri.indexOf("/"));
        String referer = protocol + "://" + uri;
        return referer;
    }

    public static void transport(InputStream in, OutputStream out) throws IOException {
        transport(in, out, 4096);
    }

    public static void transport(FileInputStream fileInputStream, OutputStream out) throws IOException {
        transport(fileInputStream, out, 4096);
    }

    // IO 实现
    public static void transport(InputStream in, OutputStream out, int buffer) throws IOException {
        byte[] bytes = new byte[buffer];
        int len;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
    }

    // NIO 实现,使用系统内存
    public static void transport(FileInputStream fileInputStream, OutputStream out, int buffer) throws IOException {
        try (FileChannel channel = fileInputStream.getChannel()) {
            ByteBuffer buff = ByteBuffer.allocateDirect(786432);  // 系统内存 1024 * 128 * 6
            byte[] bytes = new byte[buffer];
            int capacity, len;
            while ((capacity = channel.read(buff)) != -1) {
                buff.position(0);
                buff.limit(capacity);
                while (buff.hasRemaining()) {
                    len = Math.min(buff.remaining(), buffer);
                    buff.get(bytes, 0, len);
                    out.write(bytes);
                }
                buff.clear();
            }
        }
    }

}