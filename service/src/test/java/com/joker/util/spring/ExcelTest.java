package com.joker.util.spring;

import com.joker.dao.PersonDao;
import com.joker.util.ExcelUtil;
import com.joker.util.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * created by Joker on 2019/7/6
 */
public class ExcelTest extends SpringBootBaseTest {

    @Autowired
    private PersonDao personDao;

    @Test
    public void toXlsx() throws IOException {
        String[] fields = {"id", "name", "sex", "age"};
        ExcelUtil.toXlsx(new FileOutputStream("C:\\Softwares\\FTP\\person.xlsx"), personDao.selectAll(), fields);
    }

}
