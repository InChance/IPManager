package com.leo.utils;

import com.leo.model.CommandResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 统一协议返回
 */
public class ResultCode extends CommandResult{

    public static final int FAIL = 1; // 失败返回
    public static final int SUCC = 0; // 成功返回

    public ResultCode(int status, Object body) {
        super(status, body);
    }

    public static CommandResult succResult(Map<String, Object> map){
        if( map == null || map.isEmpty() ){
            map = new LinkedHashMap<>();
            map.put("state", "ok");
        }
        return new ResultCode(ResultCode.SUCC, map);
    }

    public static CommandResult succResult(Object object){
        if( object == null ){
            return succResult();
        }
        return new ResultCode(ResultCode.SUCC, object);
    }

    public static CommandResult succResult() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("state", "ok");
        return new ResultCode(ResultCode.SUCC, map);
    }

    public static CommandResult errorResult(String message) {
        Map<String, Object> mseeageMap = new LinkedHashMap<String, Object>();
        mseeageMap.put("msg", message);
        return new ResultCode(ResultCode.FAIL, mseeageMap);
    }

    public static CommandResult errorResult(Map<String, Object> map, String message) {
        map.put("msg", message);
        return new ResultCode(ResultCode.FAIL, map);
    }

}
