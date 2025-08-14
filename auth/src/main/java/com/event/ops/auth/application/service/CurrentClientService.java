package com.event.ops.auth.application.service;

import com.event.ops.auth.infrastructure.persistence.ClientRepository;
import com.event.ops.database.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public CurrentClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientEntity getCurrentClient() {
        String clientKey = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return clientRepository.findByClientKey(clientKey)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public ClientEntity getCurrentClient(String clientKey) {
        return clientRepository.findByClientKey(clientKey)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public String getClientKey() {
        return (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
