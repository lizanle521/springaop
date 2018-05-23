package com.lzl.mybatis3.chapter1;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecycleMerOrderMapper {
    int deleteByPrimaryKey(Long recycleOrderId);

    int insert(RecycleMerOrder record);

    int insertSelective(RecycleMerOrder record);

    RecycleMerOrder selectByPrimaryKey(Long recycleOrderId);

    int updateByPrimaryKeySelective(RecycleMerOrder record);

    int updateByPrimaryKey(RecycleMerOrder record);

}