package com.leo.utils;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 统一协议返回
 */
public class CommandResult {
    private static final int FAIL = 1; // 失败返回
    private static final int SUCC = 0; // 成功返回

    @Getter
    private int code = SUCC;
    @Getter
    private Object body = null;

    private CommandResult(int code, Object body) {
        this.code = code;
        this.body   = body;
    }

    public static CommandResult succResult(Map<String, Object> map){
        if( map == null || map.isEmpty() ){
            map = new LinkedHashMap<String, Object>();
            map.put("state", "ok");
        }
        return new CommandResult(CommandResult.SUCC, map);
    }

    public static CommandResult succResult(Object object){
        if( object == null ){
            return succResult();
        }
        return new CommandResult(CommandResult.SUCC, object);
    }

    public static CommandResult succResult() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("state", "ok");
        return new CommandResult(CommandResult.SUCC, map);
    }

    public static CommandResult errorResult(String message) {
        Map<String, Object> mseeageMap = new LinkedHashMap<String, Object>();
        mseeageMap.put("msg", message);
        return new CommandResult(CommandResult.FAIL, mseeageMap);
    }

    public static CommandResult errorResult(Map<String, Object> map, String message) {
        map.put("msg", message);
        return new CommandResult(CommandResult.FAIL, map);
    }

}
