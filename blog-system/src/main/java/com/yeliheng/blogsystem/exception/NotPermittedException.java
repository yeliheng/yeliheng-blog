package com.yeliheng.blogsystem.exception;

public class NotPermittedException extends ApiException {
    public NotPermittedException(String detail){
        super("PERMISSION_DENIED",403,"无权限访问该页面",detail);
    }
}
