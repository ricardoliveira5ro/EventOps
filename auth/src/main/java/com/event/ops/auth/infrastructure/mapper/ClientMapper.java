package com.event.ops.auth.infrastructure.mapper;

import com.event.ops.auth.domain.model.Client;
import com.event.ops.database.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client mapToDomain(ClientEntity entity) {
        return new Client(
            entity.getClientKey(),
            entity.getClientSecret(),
            entity.getClientName(),
            entity.isActive()
        );
    }

    public ClientEntity mapToEntity(Client domain) {
        ClientEntity entity = new ClientEntity();
        entity.setClientKey(domain.getClientKey());
        entity.setClientSecret(domain.getClientSecret());
        entity.setClientName(domain.getClientName());
        entity.setActive(domain.isActive());
        return entity;
    }
}
