package com.joker.test.junit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author: Joker Jing
 * @date: 2019/8/3
 * 测试RandomStringUtils工具类
 */
public class RandomStringUtilsTest {

    @Test
    public void test() {
        // 字母（大小写）
        String random1 = RandomStringUtils.randomAlphabetic(20);
        System.out.println(random1);

        // 数字
        String random2 = RandomStringUtils.randomNumeric(20);
        System.out.println(random2);

        // 数字
        String random3 = RandomStringUtils.randomAlphanumeric(20);
        System.out.println(random3);

        String random4 = RandomStringUtils.randomAscii(20);
        System.out.println(random4);

        String random5 = RandomStringUtils.randomGraph(20);
        System.out.println(random5);

        String random6 = RandomStringUtils.randomPrint(20);
        System.out.println(random6);

        String random7 = RandomStringUtils.random(20);
        System.out.println(random7);
    }

}
