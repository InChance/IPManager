package com.leo.utils;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 统一协议返回
 */
public class ResultCode {
    private static final int FAIL = 1; // 失败返回
    private static final int SUCC = 0; // 成功返回

    @Getter
    private int status = ResultCode.SUCC;
    @Getter
    private Object body = null;

    private ResultCode(int status, Object body) {
        this.status = status;
        this.body   = body;
    }

    public static ResultCode succResult(Map<String, Object> map){
        if( map == null || map.isEmpty() ){
            map = new LinkedHashMap<String, Object>();
            map.put("state", "ok");
        }
        return new ResultCode(ResultCode.SUCC, map);
    }

    public static ResultCode succResult(Object object){
        if( object == null ){
            return succResult();
        }
        return new ResultCode(ResultCode.SUCC, object);
    }

    public static ResultCode succResult() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("state", "ok");
        return new ResultCode(ResultCode.SUCC, map);
    }

    public static ResultCode errorResult(String message) {
        Map<String, Object> mseeageMap = new LinkedHashMap<String, Object>();
        mseeageMap.put("msg", message);
        return new ResultCode(ResultCode.FAIL, mseeageMap);
    }

    public static ResultCode errorResult(Map<String, Object> map, String message) {
        map.put("msg", message);
        return new ResultCode(ResultCode.FAIL, map);
    }

}
