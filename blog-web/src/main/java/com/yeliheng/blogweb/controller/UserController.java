package com.yeliheng.blogweb.controller;


import com.yeliheng.blogcommon.exception.UnauthorizedException;
import com.yeliheng.blogcommon.utils.RedisUtils;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.domain.Menu;
import com.yeliheng.blogsystem.domain.User;
import com.yeliheng.blogsystem.service.IMenuService;
import com.yeliheng.blogsystem.service.IPermissionService;
import com.yeliheng.blogsystem.service.IUserService;

import com.yeliheng.blogsystem.utils.TokenUtils;
import com.yeliheng.blogsystem.utils.UserUtils;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IMenuService menuService;



    @PreAuthorize("@perm.hasPerm('admin:users:add')")
    @PostMapping
    public CommonResponse<Object> add(@Validated @RequestBody User user) {
        userService.insertUser(user);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:users:edit')")
    @PutMapping
    public CommonResponse<Object> update(@RequestBody User user) {
        userService.updateUser(user);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:users:delete')")
    @DeleteMapping
    public CommonResponse<Object> delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:users:list')")
    @GetMapping()
    public CommonResponse<Object> getUserList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
            User user
            ){
        return CommonResponse.success(userService.getUserList(page,pageSize,user));
    }

    @GetMapping("/info")
    public CommonResponse<Object> getUserInfo(){
        Map<String,Object> map = new HashMap<>();

        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        if(StringUtils.isNull(loginUser)) throw new UnauthorizedException("用户凭据已过期");
        //获取角色
        Set<String> roles = permissionService.getRolesByUser(loginUser.getUser());
        //获取角色权限
        Set<String> permissions = permissionService.getMenuPermission(loginUser.getUser());


        map.put("user",loginUser.getUser());
        map.put("roles",roles);
        map.put("permissions",permissions);

        return CommonResponse.success(map);
    }

    @GetMapping("/routers")
    public CommonResponse<Object> getRouters(){
        List<Menu> menuList = menuService.getMenusByUserId(userUtils.getLoginUserId());
        return CommonResponse.success(menuService.buildMenus(menuList));
    }
}
