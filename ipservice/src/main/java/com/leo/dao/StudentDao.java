package com.leo.dao;

import com.leo.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentDao {
    String TABLE = " Student ";
    String FIELDS = " `id`, `name`, `phone` ";

    @Select("select " + FIELDS + " from " + TABLE + " where id = #{id} ")
    Student get(int id);
}
