package com.yeliheng.blogsystem.domain;

import javax.validation.constraints.NotBlank;

public class RefreshToken {
    @NotBlank
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
