package com.event.ops.auth.application.service;

import com.event.ops.auth.application.ports.AuthService;
import com.event.ops.auth.domain.model.Client;
import com.event.ops.auth.infrastructure.mapper.ClientMapper;
import com.event.ops.auth.infrastructure.persistence.ClientRepository;
import com.event.ops.common.exception.UnauthorizedException;
import com.event.ops.common.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Autowired
    public AuthServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, PasswordEncoder passwordEncoder, JWTProvider jwtProvider) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String authenticate(String clientId, String clientSecret) {
        Client client = clientRepository.findByClientId(clientId)
                            .map(clientMapper::mapToDomain)
                            .orElseThrow(() -> new UnauthorizedException("Client not found"));

        if (!client.isActive())
            throw new UnauthorizedException("Client is not active, contact your admin");

        if (!passwordEncoder.matches(clientSecret, client.getClientSecret()))
            throw new UnauthorizedException("Unable to authenticate");


        return jwtProvider.generateToken(clientId);
    }

    @Override
    public Client registerClient(String clientName) {
        String clientId = UUID.randomUUID().toString();
        String rawClientSecret = UUID.randomUUID().toString().replace("-", "");

        Client client = new Client(clientId, passwordEncoder.encode(rawClientSecret), clientName, true);

        clientRepository.save(clientMapper.mapToEntity(client));
        client.setClientSecret(rawClientSecret);

        return client;
    }
}
