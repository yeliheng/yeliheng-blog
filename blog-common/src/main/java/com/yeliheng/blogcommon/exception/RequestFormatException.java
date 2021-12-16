package com.yeliheng.blogcommon.exception;

public class RequestFormatException extends ApiException {
    public RequestFormatException(String detail) {
        super("REQUEST_FORMAT_INVALID",400,"请求格式错误",detail);
    }
}
