package com.event.ops.auth.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @NotNull
    private String clientId;

    @NotBlank
    @NotNull
    private String clientSecret;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
