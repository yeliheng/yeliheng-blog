package com.yeliheng.blogcommon.exception;

public class UnexpectedException extends ApiException{
    public UnexpectedException() {
        super("UNKNOWN_ERROR", 500, "未知系统错误", "请查看控制台输出");
    }
    public UnexpectedException from(Exception e){
        return (UnexpectedException) e;
    }
}
