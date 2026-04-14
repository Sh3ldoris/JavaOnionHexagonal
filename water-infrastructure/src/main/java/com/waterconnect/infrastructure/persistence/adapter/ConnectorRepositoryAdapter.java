package com.waterconnect.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.Connector;
import com.waterconnect.domain.port.outbound.ConnectorRepository;
import com.waterconnect.infrastructure.persistence.entity.ConnectorJpaEntity;
import com.waterconnect.infrastructure.persistence.repository.ConnectorJpaRepository;

@Repository
public class ConnectorRepositoryAdapter implements ConnectorRepository {

    private final ConnectorJpaRepository jpaRepository;

    public ConnectorRepositoryAdapter(ConnectorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Connector save(Connector connector) {
        var entity = ConnectorJpaEntity.fromDomain(connector);
        return this.jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Connector> findById(UUID id) {
        return this.jpaRepository.findById(id).map(ConnectorJpaEntity::toDomain);
    }
}
