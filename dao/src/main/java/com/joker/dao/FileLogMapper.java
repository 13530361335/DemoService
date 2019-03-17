package com.joker.dao;

import com.joker.entity.FileLog;

public interface FileLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileLog record);

    int insertSelective(FileLog record);

    FileLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileLog record);

    int updateByPrimaryKey(FileLog record);
}