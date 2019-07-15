package com.joker.sql.dao;

import com.joker.sql.entity.Role;

/**
* Created by Mybatis Generator on 2019/07/16
*/
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}