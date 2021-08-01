package com.yeliheng.blogsystem.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ErrorDTO {
    private String errCode;
    private Integer httpCode;
    private String message;
    private String detail;
    private JSONObject data = (JSONObject) JSON.parse("{}");

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
