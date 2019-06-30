package com.joker.util;

import org.junit.Test;

public class SSHTest {

    @Test
    public void scp() throws Exception {
        SSHUtil.putFile("47.105.168.197",22, "root","Lxq931129", "C:\\Download","*","/xq/test");
    }

}
