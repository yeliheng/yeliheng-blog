package com.yeliheng.blogcommon.exception;

public class GeneralException extends ApiException{
    public GeneralException(String detail) {
        super("GENERAL_EXCEPTION",200,"通用异常",detail);
    }
}
