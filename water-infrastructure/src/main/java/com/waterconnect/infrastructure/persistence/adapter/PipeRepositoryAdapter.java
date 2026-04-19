package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.enums.PipeStatus;
import com.waterconnect.domain.port.outbound.PipeRepository;
import com.waterconnect.infrastructure.persistence.entity.PipeJpaEntity;
import com.waterconnect.infrastructure.persistence.repository.PipeJpaRepository;

@Repository
public class PipeRepositoryAdapter implements PipeRepository {

    private final PipeJpaRepository jpaRepository;

    public PipeRepositoryAdapter(PipeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Pipe save(Pipe pipe) {
        var entity = PipeJpaEntity.fromDomain(pipe);
        return this.jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Pipe> findById(UUID id) {
        return this.jpaRepository.findById(id).map(PipeJpaEntity::toDomain);
    }

    @Override
    public List<Pipe> findAll(String material, String status) {
        return this.jpaRepository
                .findByMaterialAndStatus(PipeMaterial.fromString(material), PipeStatus.fromString(status))
                .stream()
                .map(PipeJpaEntity::toDomain)
                .toList();
    }
}
