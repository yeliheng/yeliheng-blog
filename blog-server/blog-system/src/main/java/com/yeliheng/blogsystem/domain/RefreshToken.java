package com.yeliheng.blogsystem.domain;

import javax.validation.constraints.NotBlank;

public class RefreshToken {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
