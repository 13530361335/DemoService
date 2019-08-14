package com.joker.sql.dao;

import com.joker.sql.entity.User;

/**
* @author: Administrator
* @date: 2019/08/13
*/
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}