package com.yeliheng.blogcommon.exception;

public class NotFoundException extends ApiException {
    public NotFoundException(String detail){
        super("ENTITY_NOT_FOUND",404,"找不到该资源",detail);
    }
}
