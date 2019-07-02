package com.joker.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by Joker on 2019/7/2
 */
public class IOUtil {

    public static void transport(InputStream in, OutputStream out) throws IOException {
        transport(in, out, 4096);
    }

    public static void transport(InputStream in, OutputStream out, int buffer) throws IOException {
        byte[] bytes = new byte[buffer];
        int len;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
    }

}
