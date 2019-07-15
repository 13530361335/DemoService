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
public class Role {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限
     */
    private Integer permission;
}