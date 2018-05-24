package com.leo.dao;

import com.leo.model.IPModel;
import com.leo.model.MaskModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaskDao {
    String TABLE = " Mask ";
    String FIELDS = " `netAddress`, `maskAddress`, `recordTime` ";

    @Select("select " + FIELDS + " from " + TABLE + " where netAddress = #{netIp} limit 1")
    MaskModel getByNetIp(String netIp);

    @Insert("insert into " + TABLE + " ( " + FIELDS + " ) value ( #{netAddress}, #{maskAddress}, #{recordTime} )")
    void insert(MaskModel mask);

    @Delete("delete from " + TABLE + " where netAddress = #{netIp}")
    void delete(String netIp);

    @Select("select " + FIELDS + " from " + TABLE + " order by recordTime desc")
    List<MaskModel> getAll();
}
