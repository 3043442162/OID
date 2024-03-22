package com.ainetwork.util;

public enum ResultCode {
    SUCCEED(200, "成功"),
    ERROR(400, "失败"),
    NOT_LOGIN(403, "没有登录"),
    NULL_VALUE(500, "参数不能为空"),
    CODE_ERROR(1400, "验证码不正确");


    private Integer code;
    private String msg;
    ResultCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
