package com.yeliheng.blogsystem.common;

import com.alibaba.fastjson.JSONObject;

public class CommonResponse<T> {
    private String errCode;
    private Integer httpCode;
    private String message;
    private T data;

    protected CommonResponse() {
    }
    protected CommonResponse(String errCode, Integer httpCode, String message, T data) {
        this.errCode = errCode;
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data){
        return new CommonResponse<T>("",200,"调用成功!",data);
    }

    public static CommonResponse<Object> success(){
        return new CommonResponse<>("", 200, "调用成功!", JSONObject.parse("{}"));
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
