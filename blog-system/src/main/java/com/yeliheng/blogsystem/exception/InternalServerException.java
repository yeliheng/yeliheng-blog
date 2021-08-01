package com.yeliheng.blogsystem.exception;

public class InternalServerException extends ApiException{

    public InternalServerException(String detail) {
        super("INTERNAL_SERVER_ERROR",500,"服务器内部错误",detail);
    }
}
