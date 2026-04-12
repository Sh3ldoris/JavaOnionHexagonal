package com.waterconnect.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String entity, UUID id) {
        super(entity + " not found with id: " + id);
    }
}
