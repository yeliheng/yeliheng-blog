package com.yeliheng.blogframework.security.handle;


import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yeliheng.blogsystem.dto.ErrorDTO;
import com.yeliheng.blogcommon.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

/**
 * 认证失败处理类 返回未授权
 *
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
    {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrCode("USER_UNAUTHORIZED");
        errorDTO.setMessage("用户授权未通过");
        errorDTO.setHttpCode(401);
        errorDTO.setDetail("认证失败!");

        ServletUtils.renderString(response, JSON.toJSONString(errorDTO),401);
    }
}