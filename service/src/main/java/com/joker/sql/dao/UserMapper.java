package com.joker.sql.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joker.sql.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
public interface UserMapper extends BaseMapper<User>{

    /**
     * 根据用户账号查询用户信息
     * @param account
     * @return
     */
    List<User> selectByAccount(String account);

}