package com.joker.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * created by Joker on 2019/7/6
 */
@Setter
@Getter
@ToString(exclude = {"age"})
public class Person {

    private String id;
    private String name;
    private String sex;
    private String age;

}
