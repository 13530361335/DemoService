package com.joker.test.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Properties;

/**
 * @author Joker Jing
 * @date 2019/8/13
 */
@Slf4j
public class SystemPropertiesTest {

    @Test
    public void getAll(){
        Properties props = System.getProperties();
        props.list(System.out);
    }

    @Test
    public void getUser(){
        String user = System.getProperty("user.name");
        log.info(user);
    }

}
