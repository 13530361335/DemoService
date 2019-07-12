package com.joker.util.spring;

import com.joker.dao.PersonMapper;
import com.joker.entity.Person;
import com.joker.util.JSONUtil;
import com.joker.util.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by Joker on 2019/7/12
 */
public class SQLTest extends SpringBootBaseTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void select() {
        Person person = personMapper.selectByPrimaryKey(1L);
        System.out.println(person);
    }

}
