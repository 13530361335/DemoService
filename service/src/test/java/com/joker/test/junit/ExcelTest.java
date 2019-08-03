package com.joker.test.junit;

import com.joker.sql.entity.User;
import com.joker.util.ExcelUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * created by Joker on 2019/7/6
 */
public class ExcelTest {

    @Test public void toData() throws IOException {
        String[] fields = {"id", "name", "sex", "age"};
        List<User> people =
            ExcelUtil.toData(new FileInputStream("C:\\Softwares\\FTP\\person.xlsx"), 0, 1, fields, User.class);
        System.out.println(people);
    }

}
