package com.joker.test.junit;

import com.joker.sql.entity.User;
import com.joker.util.ExcelUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Joker on 2019/7/6
 */
public class ExcelTest {

    private String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    private String user = "root";
    private String password = "Jing#123";

    @Test
    public void toExcel() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        String sql = "select * from person";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        Field[] fields = User.class.getDeclaredFields();
        List<User> people = new ArrayList<>();
        while (resultSet.next()) {
            User person = new User();
            for (Field field : fields) {
                String value = resultSet.getString(field.getName());
                String setMethod = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                person.getClass().getMethod(setMethod, String.class).invoke(person, value);
            }
            people.add(person);
        }
        conn.commit();
        conn.close();
        String[] fields1 = {"id", "name", "sex", "age"};
        ExcelUtil.toExcel(new FileOutputStream("C:\\Softwares\\FTP\\person.xlsx"), people, fields1);
    }

    @Test
    public void toData() throws IOException {
        String[] fields = {"id", "name", "sex", "age"};
        List<User> people = ExcelUtil.toData(new FileInputStream("C:\\Softwares\\FTP\\person.xlsx"), 0, 1, fields, User.class);
        System.out.println(people);
    }

}
