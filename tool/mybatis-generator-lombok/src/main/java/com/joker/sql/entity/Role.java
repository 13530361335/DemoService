package com.joker.sql.entity;

import lombok.Data;

/**
* Created by Mybatis Generator on 2019/07/28
*/
@Data
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