package com.joker.util.spring;

import com.joker.entity.Person;
import com.joker.util.JSONUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Joker on 2019/7/6
 */
public class SqlTest {

    private String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    private String user = "root";
    private String password = "Jing#123";

    @Test
    public void select() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        String sql = "select * from person";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        Field[] fields = Person.class.getDeclaredFields();

        List<Person> persons = new ArrayList<>();
        while (resultSet.next()) {
            Person person = new Person();
            for (Field field : fields) {
                String value = resultSet.getString(field.getName());
//                person.getClass().getField(field.getName()).setAccessible(true);
//                person.getClass().getField(field.getName()).set(person,value);
                String setMethod = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                person.getClass().getMethod(setMethod,String.class).invoke(person,value);
            }
            persons.add(person);
        }
        System.out.println(persons);
        conn.commit();
        conn.close();
    }

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = Person.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            //设置是否允许访问，不是修改原来的访问权限修饰词。
            System.out.println(fields[i].getName());
//            fields[i].setAccessible(true);
//            System.out.println(fields[i].getName()+":"+fields[i].get(st));
        }
    }


}
