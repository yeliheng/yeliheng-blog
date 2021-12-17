package com.yeliheng.blogframework.security.handle;

import com.alibaba.fastjson.JSON;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.dto.ErrorDTO;
import com.yeliheng.blogsystem.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        try{
            LoginUser loginUser = tokenUtils.getLoginUser(httpServletRequest);
            if(StringUtils.isNotNull(loginUser))
                tokenUtils.deleteLoginUser(loginUser.getUser().getId());
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrCode("");
            errorDTO.setMessage("登出成功!");
            errorDTO.setHttpCode(200);
            ServletUtils.renderString(httpServletResponse, JSON.toJSONString(errorDTO),200);
        }catch (Exception ignored) {}

    }
}
