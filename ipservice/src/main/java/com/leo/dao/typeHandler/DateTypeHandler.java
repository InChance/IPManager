package com.leo.dao.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedTypes(Date.class)
@MappedJdbcTypes({JdbcType.TIMESTAMP})
public class DateTypeHandler extends BaseTypeHandler<Date>{

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date date, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new Timestamp(date.getTime()));
    }

    @Override
    public Date getNullableResult(ResultSet rs, String s) throws SQLException {
        String sqlTimestamp = rs.getString(s);
        return toParse(sqlTimestamp);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int i) throws SQLException {
        String sqlTimestamp = rs.getString(i);
        return toParse(sqlTimestamp);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int i) throws SQLException {
        String sqlTimestamp = cs.getString(i);
        return toParse(sqlTimestamp);
    }

    private Date toParse(String sqlTimestamp){
        if(sqlTimestamp != null){
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sqlTimestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

