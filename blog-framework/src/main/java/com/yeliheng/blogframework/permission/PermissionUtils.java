package com.yeliheng.blogframework.permission;

import com.yeliheng.blogcommon.constant.Constants;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.service.IPermissionService;

import com.yeliheng.blogsystem.utils.TokenUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * 自定义权限控制器实现
 */
@Service("perm")
public class PermissionUtils {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private IPermissionService permissionService;

    /**
     *
     * 判断是否具有某个权限
     * @param permission 权限字符串
     * @return true/false
     */
    public boolean hasPerm(String permission){
        if(StringUtils.isEmpty(permission)) return false;
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser))
        {
            return false;
        }
        //获取角色权限
        Set<String> permissions = permissionService.getMenuPermission(loginUser.getUser());
        if(CollectionUtils.isEmpty(permissions)) return false;
        return permissions.contains(Constants.ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }
}
