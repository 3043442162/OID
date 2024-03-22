package com.ainetwork.util;

import lombok.Data;

@Data
public class Result<T> {
    private String msg;
    private T data;
    private Integer code;
    public Result(String msg, T data ,Integer code){
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public Result(){

    }

    public Result(String msg, Integer code){
        this.msg = msg;
        this.code = code;
    }
    public static <C> Result<C> ok(C data) {
        return ok(ResultCode.SUCCEED, data);
    }

    public static <C> Result<C> ok(ResultCode code) {
        return new Result<C>(code.getMsg(), code.getCode());
    }

    public static <C> Result<C> ok(ResultCode code, C data) {
        return new Result<C>(code.getMsg(), data, code.getCode());
    }

    public static <C> Result<C> ok() {
        return ok(ResultCode.SUCCEED, null);
    }

    public static <C> Result<C> error(ResultCode resultCode, C data) {
        return new Result<C>(resultCode.getMsg(), data, resultCode.getCode());
    }

    public static <C> Result<C> error() {
        return error(ResultCode.ERROR, null);
    }

    public static <C> Result<C> error(C data) {
        return error(ResultCode.ERROR, data);
    }

    public static <C> Result<C> error(ResultCode resultCode) {
        return error(resultCode, null);
    }


}
