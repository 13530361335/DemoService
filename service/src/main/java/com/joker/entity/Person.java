package com.joker.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"age"})
public class Person {
    private Long id;
    private String name;
    private String sex;
    private Integer age;
}