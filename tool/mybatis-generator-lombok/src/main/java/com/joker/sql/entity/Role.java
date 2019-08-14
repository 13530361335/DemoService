package com.joker.sql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author: Administrator
* @date: 2019/08/13
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
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