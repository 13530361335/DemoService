package com.joker.test.util;

import com.joker.sql.entity.User;
import com.joker.util.ExcelUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Joker Jing
 * @date: 2019/7/6
 */
public class ExcelTest {

    @Test
    public void toData() throws IOException {
        List<String> fields = Arrays.asList("user_id", "role_id", "account",
                "password", "real_name", "telephone",
                "email", "create_time", "update_time");
        ExcelUtil.toData(
                new FileInputStream("C:\\Users\\Administrator\\Desktop\\无标题.xlsx"),
                0, 1, fields, User.class)
                .forEach(System.out::println);
    }

    @Test
    public void toExcel() throws IOException {
        List<String> fields = Arrays.asList("user_id", "role_id", "account",
                "password", "real_name", "telephone",
                "email", "create_time", "update_time");

        List<User> users = ExcelUtil.toData(
                new FileInputStream("C:\\Users\\Administrator\\Desktop\\无标题.xlsx"),
                0, 1, fields, User.class);

        ExcelUtil.toExcel(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\无标题1.xlsx"),
                users, fields.stream().filter(e -> !"password".equals(e)).collect(Collectors.toList()));
    }

}
