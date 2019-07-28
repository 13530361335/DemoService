package com.joker.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Random;

/**
 * created by Joker on 2019/7/3
 * AES加密：ECB加密模式
 */
@Slf4j
public class AESUtil {

    /**
     * 默认加密的KEY,使用AES-128-ECB加密模式，key需要为16位
     */
    private static final String DEFAULT_KEY = "8G5M4Ff9hel8fUA9";

    private static final int DEFAULT_KEY_LENGTH = 16;

    /**
     * 加密
     *
     * @param plainText 明文
     * @param key       密钥
     * @return
     */
    public static String encrypt(String plainText, String key) {
        if (key == null || key.length() != DEFAULT_KEY_LENGTH) {
            key = DEFAULT_KEY;
            log.debug("密匙长度小于16,使用默认密匙");
        }
        try {
            // 获取AES算法需要的秘钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            // 设置ECB模式
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //"算法/模式/补码方式"
            // 设置加密模式并加入密匙
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            // 进行加密
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes("utf-8"));
            // 返回加密字符串
            return Base64.getEncoder().encodeToString(cipherBytes);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param cipherText 密文
     * @param key        密钥
     * @return
     */
    public static String decrypt(String cipherText, String key) {
        if (key == null || key.length() != DEFAULT_KEY_LENGTH) {
            key = DEFAULT_KEY;
            log.debug("密匙长度小于16,使用默认密匙");
        }
        try {
            // 获取AES算法需要的秘钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            // 设置ECB模式
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 设置解密模式并加入密匙
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            // 进行解密
            byte[] textBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            // 返回解密字符串
            return new String(textBytes, "utf-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}

