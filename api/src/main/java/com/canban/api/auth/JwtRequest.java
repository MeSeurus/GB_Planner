package com.canban.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT-запроc")
public class JwtRequest {
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Пароль пользователя")
    private String password;

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
}
