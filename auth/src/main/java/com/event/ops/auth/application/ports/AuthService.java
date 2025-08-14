package com.event.ops.auth.application.ports;

import com.event.ops.auth.domain.model.Client;

public interface AuthService {
    String authenticate(String clientKey, String clientSecret);
    Client registerClient(String clientName);
}
