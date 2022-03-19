package com.yeliheng.blogsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    @Excel(name = "用户ID")
    @Id
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4,max = 20,message = "用户名不能超过20个字符")
    @Excel(name = "用户名", width = 25)
    private String username;

    private String password;

    @Excel(name = "昵称", width = 20)
    @Size(max = 30, message = "昵称不能超过30个字符")
    private String nickname;

    @Excel(name = "手机号", width = 20)
    @Size(max = 11, message = "手机号不能超过11个字符")
    private String phone;

    @Excel(name = "电子邮箱", width = 25)
    @Email(message = "邮箱格式不正确")
    @Size(max = 30, message = "邮箱不能超过30个字符")
    private String email;

    private String avatar;

    @Excel(name = "状态",replace = {"正常_false","锁定_true"})
    private Boolean locked;

    private Long[] roles;

    private List<Role> roleList;

    public boolean isAdmin()
    {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long[] getRoles() {
        return roles;
    }

    public void setRoles(Long[] roles) {
        this.roles = roles;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
