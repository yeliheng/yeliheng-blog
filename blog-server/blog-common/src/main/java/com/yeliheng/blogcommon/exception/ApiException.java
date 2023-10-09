package com.yeliheng.blogcommon.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ApiException extends RuntimeException{
    protected String errCode;
    protected Integer httpCode;
    protected String errMsg;
    protected String detail;
    public ApiException(String errCode,Integer httpCode,String errMsg,String detail){
        this.errCode = errCode;
        this.httpCode = httpCode;
        this.errMsg = errMsg;
        this.detail = detail;
    }

    public ApiException(String errCode,Integer httpCode,String errMsg){

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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("errCode", getErrCode())
            .append("httpCode", getHttpCode())
            .append("errMsg", getErrMsg())
            .append("detail", getDetail())
            .toString();
    }
}
