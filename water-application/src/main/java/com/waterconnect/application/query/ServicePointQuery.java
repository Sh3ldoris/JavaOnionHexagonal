package com.waterconnect.application.query;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.dto.ServicePointResultDto;
import com.waterconnect.application.dto.mapper.ServicePointResultDtoMapper;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.ServicePointRepository;

@Service
public class ServicePointQuery {

    private final ServicePointRepository servicePointRepository;

    public ServicePointQuery(ServicePointRepository servicePointRepository) {
        this.servicePointRepository = servicePointRepository;
    }

    /**
     * Get ServicePoint by provided ID
     * @return Result DTO for ServicePoint
     * @throws EntityNotFoundException in a case no ServicePoint founded
     */
    @Transactional
    public ServicePointResultDto getById(UUID servicePointId) throws EntityNotFoundException {
        var servicePoint = this.servicePointRepository.findById(servicePointId).orElse(null);

        if (Objects.isNull(servicePoint)) {
            throw new EntityNotFoundException("ServicePoint", servicePointId);
        }

        return ServicePointResultDtoMapper.fromDomain(servicePoint);
    }
}
