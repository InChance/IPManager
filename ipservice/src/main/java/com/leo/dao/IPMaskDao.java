package com.leo.dao;

import com.leo.model.IPMaskModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IPMaskDao {
    String TABLE = " IPMask ";
    String FIELDS = " `id`, `ip`, `name`, `collectTime` ";

    @Select("select " + FIELDS + " from " + TABLE + " where id = #{id} ")
    IPMaskModel get(int id);
}
