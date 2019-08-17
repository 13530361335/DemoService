package com.joker.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description: 统一常量存放
 */
@Slf4j
public class HttpUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }

    /**
     * 文件下载时设置响应头
     *
     * @param fileName
     * @return
     */
    public static void setFileHeader(String fileName) {
        String realFileName = null;
        try {
            // 解决不同浏览器下载时文件名乱码
            realFileName = isIeBrowser() ?
                    fileName.replaceAll("\\+", "%20") :
                    new String(fileName.getBytes(Charset.defaultCharset()), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        HttpServletResponse response = getResponse();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + realFileName + "\"");
        response.setHeader("Connection", "close");
    }

    public static void setImageHeader() {
        HttpServletResponse response = getResponse();
        response.setHeader("Content-Type", "image/png");
        response.setHeader("Connection", "close");
    }

    /**
     * 判断客户端是否为ie浏览器
     *
     * @return
     */
    private static boolean isIeBrowser() {
        String[] iEs = {"MSIE", "Trident", "Edge"};
        HttpServletRequest request = getRequest();
        String userAgent = request.getHeader("User-Agent");
        for (String ie : iEs) {
            if (userAgent.contains(ie)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 输入流转字符串
     * @param in
     * @return
     */
    public static String streamToString(InputStream in) {
        try {
            byte[] data = new byte[in.available()];
            in.read(data);
            return Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }


    /**
     * 字符串转输出流
     * @param string
     * @param out
     * @return
     */
    public static void stringToStream(String string, OutputStream out) {
        try {
            byte[] b = Base64.getDecoder().decode(string);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
