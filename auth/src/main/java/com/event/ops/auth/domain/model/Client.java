package com.event.ops.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

    private String clientId;
    private String clientSecret;
    private String clientName;
    private boolean active;
}
