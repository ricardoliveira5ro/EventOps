package com.event.ops.auth.infrastructure.web.controller;

import com.event.ops.auth.application.service.AuthServiceImpl;
import com.event.ops.auth.domain.model.Client;
import com.event.ops.auth.infrastructure.web.dto.LoginRequest;
import com.event.ops.auth.infrastructure.web.dto.LoginResponse;
import com.event.ops.auth.infrastructure.web.dto.RegisterClientRequest;
import com.event.ops.auth.infrastructure.web.dto.RegisterClientResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${admin.api.key}")
    private String adminApiKey;

    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getClientId(), loginRequest.getClientSecret());

        return ResponseEntity.ok(new LoginResponse(token, "1 hour"));
    }

    @PostMapping("/register-client")
    public ResponseEntity<?> registerUser(HttpServletRequest request, @RequestBody RegisterClientRequest registerClientRequest) {
        if (!adminApiKey.equals(request.getHeader("X-Admin-Key"))) {
            throw new RuntimeException("This action is not allowed"); // Custom exception
        }

        Client client = authService.registerClient(registerClientRequest.getClientName());

        return ResponseEntity.ok(new RegisterClientResponse(client.getClientId(), client.getClientSecret()));
    }
}
