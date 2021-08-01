package com.yeliheng.blogsystem.exception;

import com.yeliheng.blogsystem.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ApiExceptionHandlerAdvice{

    @ExceptionHandler(value = Exception.class)
    //由ApiException接管Service异常和系统异常
    public ResponseEntity<ErrorDTO> exception(Exception exception, HttpServletResponse response){
        ErrorDTO errorDTO = new ErrorDTO();
        if(exception instanceof ApiException){
            ApiException apiException = (ApiException) exception;
            errorDTO.setErrCode(apiException.getErrCode());
            errorDTO.setMessage(apiException.getErrMsg());
            errorDTO.setHttpCode(apiException.getHttpCode());
            errorDTO.setDetail(apiException.getDetail());
        }else if(exception instanceof NoHandlerFoundException) {
            errorDTO.setErrCode("ENTITY_NOT_FOUND");
            errorDTO.setMessage("404 Not Found");
            errorDTO.setHttpCode(404);
            errorDTO.setDetail("");
        }else if(exception instanceof HttpRequestMethodNotSupportedException){
            errorDTO.setErrCode("METHOD_NOT_ALLOWED");
            errorDTO.setMessage("请求方法不支持");
            errorDTO.setHttpCode(405);
            errorDTO.setDetail("请修改请求方法");
        }else{
            errorDTO.setErrCode("UNKNOWN_ERROR");
            errorDTO.setMessage("未知系统错误");
            errorDTO.setHttpCode(500);
            errorDTO.setDetail("请查看日志输出");
            exception.printStackTrace();
            /*            try {
                throw new UnexpectedException().from(exception);
            } catch (UnexpectedException e) {
                e.printStackTrace();
            }*/
        }

        return new ResponseEntity<>(errorDTO,HttpStatus.valueOf(errorDTO.getHttpCode()));
    }

}
