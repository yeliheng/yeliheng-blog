package com.yeliheng.blogsystem.security.handle;

import com.alibaba.fastjson.JSON;
import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.dto.ErrorDTO;
import com.yeliheng.blogsystem.entity.LoginUser;
import com.yeliheng.blogsystem.utils.ServletUtils;
import com.yeliheng.blogsystem.utils.StringUtils;
import com.yeliheng.blogsystem.utils.TokenUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
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
    @Autowired
    private UserUtils userUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenUtils.getLoginUser(httpServletRequest);
        if(StringUtils.isNotNull(loginUser))
            tokenUtils.deleteLoginUser(loginUser.getUser().getId());
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrCode("");
        errorDTO.setMessage("登出成功!");
        errorDTO.setHttpCode(200);

        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(errorDTO),200);
    }
}
