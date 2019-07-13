package com.joker.test.spring;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.joker.test.SpringBootBaseTest;
import com.joker.sql.dao.PersonMapper;
import com.joker.sql.entity.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * created by Joker on 2019/7/12
 */
public class SQLTest extends SpringBootBaseTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void select() {
        PageHelper.offsetPage(1, 2);
        List<Person> people = personMapper.selectAll();
        PageInfo<Person> pageInfo = new PageInfo<>(people);
        System.out.println(pageInfo);
    }

}
