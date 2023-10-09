package com.yeliheng.blogsystem.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

public class RegisterUser {
    @Schema(title = "用户名")
    @NotBlank
    private String username;

    @Schema(title = "密码")
    @NotBlank
    private String password;

    @Schema(title = "确认密码")
    @NotBlank
    private String password_confirm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }
}
