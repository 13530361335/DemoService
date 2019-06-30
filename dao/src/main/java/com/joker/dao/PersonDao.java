package com.joker.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface PersonDao {

    @Select("select * from person where id = #{id}")
    Map selectById(String id);

}
