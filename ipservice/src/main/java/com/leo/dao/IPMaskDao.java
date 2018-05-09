package com.leo.dao;

import com.leo.model.IPMaskModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IPMaskDao {
    String TABLE = " IPMask ";
    String FIELDS = " `ip`, `name`, `collectTime` ";

    @Select("select " + FIELDS + " from " + TABLE + " where ip = #{ip} limit 1")
    IPMaskModel getByIp(String ip);

    @Insert("insert into " + TABLE + " ( " + FIELDS + " ) value ( #{ip}, #{name}, #{collectTime} )")
    void insert(IPMaskModel ipMask);

    @Update("update " + TABLE + " set name = #{ipMask.name} where ip = #{ipMask.ip}")
    void update(@Param("ipMask") IPMaskModel ipMask);

    @Delete("delete from " + TABLE + " where ip = #{ip}")
    void delete(String ip);
}
