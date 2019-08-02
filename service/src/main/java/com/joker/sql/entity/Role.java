package com.joker.sql.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
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