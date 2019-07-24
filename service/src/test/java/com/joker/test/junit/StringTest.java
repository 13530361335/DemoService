package com.joker.test.junit;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * created by Joker on 2019/7/24
 */
public class StringTest {

    @Test
    public void test1() {
        String str = "{0}/{1}/{2}";
        String format = MessageFormat.format(str, "a", "b", 3);
        System.out.println(format);
    }

    @Test
    public void test2() {
        String format = String.format("%s/%s/%s",1, 2, 3);
        System.out.println(format);
        System.out.println( '\u0000');

    }

    @Test
    public void test3() {
        String[] command = {"a", "b", "c"};
        String str = "";
        for (int i = 0; i < command.length;i++) {
            if(i == 0){
                str = "\"{" + i + "}\"";
            }else {
                str += " \"{" + i + "}\"";
            }
        }
        String format = MessageFormat.format(str, command);
        System.out.println(format);
    }

    @Test
    public void test4() {
        String command = "java \"-version\"";
        System.out.println(command);
    }
}
