package com.joker.test.util;

import com.joker.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author Joker Jing
 * @date 2019/7/14
 */
@Slf4j
public class AESTest {

    @Test
    public void encrypt() {
        String plainText = "root";
        String key = RandomStringUtils.randomAlphanumeric(16);
        log.info("密码：" + plainText);
        log.info("密钥：" + key);
        // 加密
        String cipherText = AesUtil.encrypt(plainText, key);
        log.info("加密后的字串是：" + cipherText);
        // 解密
        String plainText1 = AesUtil.decrypt(cipherText, key);
        log.info("解密后的字串是：" + plainText1);
    }

}
