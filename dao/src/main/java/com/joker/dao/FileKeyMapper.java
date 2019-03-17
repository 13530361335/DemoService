package com.joker.dao;

import com.joker.entity.FileKey;

public interface FileKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileKey record);

    int insertSelective(FileKey record);

    FileKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileKey record);

    int updateByPrimaryKey(FileKey record);
}