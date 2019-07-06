package com.joker.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PersonDao {

    @Select("select * from person where id = #{id}")
    Map selectById(String id);

    @Select("select * from person")
    List<Map> selectAll();

}
