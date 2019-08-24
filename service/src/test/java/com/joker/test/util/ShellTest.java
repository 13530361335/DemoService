package com.joker.test.util;

import com.joker.util.ShellUtil;
import org.junit.Test;

/**
 * @author: Joker Jing
 * @date: 2019/8/8
 */
public class ShellTest {

    @Test
    public void test(){
        ShellUtil.execute("java","-version");
        ShellUtil.execute("git","--version");
    }

}
