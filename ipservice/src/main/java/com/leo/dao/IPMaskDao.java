package com.leo.dao;

import com.leo.model.IPMaskModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IPMaskDao {
    String TABLE = " IPMask ";
    String FIELDS = " `ip`, `name`, `collectTime` ";

    @Select("select " + FIELDS + " from " + TABLE + " where ip = #{ip} limit 1")
    IPMaskModel getByIp(String ip);

    @Insert("insert into " + TABLE + " ( " + FIELDS + " ) value ( #{ip}, #{name}, #{collectTime} )")
    void insert(IPMaskModel ipMask);

    @Update("update " + TABLE + " set ip = #{ip}, name = #{name} where ip = #{ip}")
    void update(IPMaskModel ipMask);
}
