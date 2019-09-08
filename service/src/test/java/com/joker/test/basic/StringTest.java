package com.joker.test.basic;

import org.junit.Test;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * @author Joker Jing
 * @date 2019/7/24
 */
public class StringTest {

    /**
     * 字符串替换1
     */
    @Test
    public void test1() {
        String str = "{0}/{1}/{2}";
        String format = MessageFormat.format(str, "a", "b", 3);
        System.out.println(format);
    }

    /**
     * 字符串替换2
     */
    @Test
    public void test2() {
        String format = String.format("%s/%s/%s", 1, 2, 3);
        System.out.println(format);
        System.out.println('\u0000');

    }

    @Test
    public void test5() {
        //获取系统默认的字符编码
        System.out.println(Charset.defaultCharset());
        //获取系统默认语言
        System.out.println(System.getProperty("user.language"));
    }

    @Test
    public void test6() {
        Double result = 1.414 * 1.414 > 2 ? 1.413 : 1.414;
        double dian = 0.001;
        while (result.toString().length() < 12) {
            dian *= 0.1;
            for (int i = 1; i < 10; i++) {
                if ((result + dian * i) * (result + dian * i) > 2) {
                    result = result + dian * (i - 1);
                    System.out.println(result);
                    break;
                }
            }
        }
        System.out.println("计算结果:" + result);
        System.out.println("校验结果");
        System.out.println(result * result);
        System.out.println((result + 0.0000000001) * (result + 0.0000000001));
    }

    @Test
    public void test7() {
        double low = 1.41, high = 1.42;
        double mid = (high + low) / 2;
        double dian = 0.0000000001;
        System.out.println(String.valueOf(mid).length());
        while ((high - low) > dian) {
            System.out.println(mid);
            if (mid * mid < 2) {
                low = mid;
            } else {
                high = mid;
            }
            mid = (high + low) / 2;
        }
        System.out.println(mid);
    }

}
