package com.leo.dao;

import com.leo.model.IPModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPDao {
    String TABLE = " IP ";
    String FIELDS = " `ip`, `name`, `collectTime` ";

    @Select("select " + FIELDS + " from " + TABLE + " where ip = #{ip} limit 1")
    IPModel getByIp(String ip);

    @Insert("insert into " + TABLE + " ( " + FIELDS + " ) value ( #{ip}, #{name}, #{collectTime} )")
    void insert(IPModel ipMask);

    @Update("update " + TABLE + " set name = #{ipMask.name} where ip = #{ipMask.ip}")
    void update(@Param("ipMask") IPModel ipMask);

    @Delete("delete from " + TABLE + " where ip = #{ip}")
    void delete(String ip);

    @Select("select " + FIELDS + " from " + TABLE)
    List<IPModel> getAll();
}
