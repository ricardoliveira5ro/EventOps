package com.event.ops.auth.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterClientResponse {

    private String clientId;
    private String clientSecret;
}
