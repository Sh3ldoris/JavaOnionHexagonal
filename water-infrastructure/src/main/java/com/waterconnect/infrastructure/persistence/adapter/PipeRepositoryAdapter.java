package com.waterconnect.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.Pipe;
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
}
