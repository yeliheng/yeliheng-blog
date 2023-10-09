package com.yeliheng.blogcommon.exception;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String detail) {
        super("USER_UNAUTHORIZED", 401, "用户授权未通过", detail);
    }
}
