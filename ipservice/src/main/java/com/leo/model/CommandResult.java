package com.leo.model;

import com.leo.utils.ResultCode;
import lombok.Getter;
/**
 * 协议返回的信息体
 */
public abstract class CommandResult {
    @Getter
    private int status = ResultCode.SUCC;
    @Getter
    private Object body = null;

    protected CommandResult( int status, Object body ){
        this.status = status;
        this.body   = body;
    }
}
