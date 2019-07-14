package com.joker.test.junit;

import com.joker.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * created by Joker on 2019/7/14
 */
@Slf4j
public class AESTest {

    @Test
    public void encrypt() {
        String plainText = "Jing#123";
        String key = RandomStringUtils.randomAlphanumeric(16);
        log.info("加密前的字串是：" + plainText);
        log.info("密钥：" + key);
        // 加密
        String cipherText = AESUtil.encrypt(plainText, key);
        log.info("加密后的字串是：" + cipherText);
        // 解密
        String plainText1 = AESUtil.decrypt(cipherText, key);
        log.info("解密后的字串是：" + plainText1);
    }

}
