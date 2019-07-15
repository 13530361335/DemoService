package com.joker.sql.entity;

import lombok.Getter;
import lombok.Setter;;
import lombok.ToString;

/**
* Created by Mybatis Generator on 2019/07/16
*/
@Getter
@Setter
@ToString
public class User {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;
}