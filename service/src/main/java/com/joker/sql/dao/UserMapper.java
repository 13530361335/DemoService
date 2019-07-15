package com.joker.sql.dao;

import com.joker.sql.entity.User;

/**
* Created by Mybatis Generator on 2019/07/16
*/
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}