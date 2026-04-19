package com.waterconnect.application.query;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.dto.ConnectorResultDto;
import com.waterconnect.application.dto.mapper.ConnectorResultDtoMapper;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.ConnectorRepository;

@Service
public class ConnectorQuery {

    private final ConnectorRepository connectorRepository;

    public ConnectorQuery(ConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    /**
     * Get connector by provided Connector ID
     * @return Result DTO for connector
     * @throws EntityNotFoundException in a case no Connector founded
     */
    @Transactional
    public ConnectorResultDto getById(UUID connectorId) throws EntityNotFoundException {
        var connectorOptional = this.connectorRepository.findById(connectorId);

        if (connectorOptional.isEmpty()) {
            throw new EntityNotFoundException("Connector",  connectorId);
        }

        return ConnectorResultDtoMapper.fromDomain(connectorOptional.get());
    }
}
