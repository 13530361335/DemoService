package com.joker.test.junit;

import com.joker.util.ShellUtil;
import org.junit.Test;

/**
 * @author: Joker Jing
 * @date: 2019/8/8
 */
public class ShellTest {

    @Test
    public void test(){
//        ShellUtil.execute("java","-version");
        ShellUtil.execute("git","--version");
        ShellUtil.execute("git","clone","-b","master","https://github.com/13530361335/DemoService.git","C:/test/demo");
    }

}
