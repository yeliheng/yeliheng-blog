package com.yeliheng.blogsystem.exception;

public class MethodNotSupportedException extends ApiException{
    public MethodNotSupportedException(String detail) {
        super("METHOD_NOT_SUPPORTED", 405, "请求方法错误",detail);
    }
}
