package com.joker.sql.dao;

import com.joker.sql.entity.Role;

/**
* @author: Administrator
* @date: 2019/08/13
*/
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}