package com.waterconnect.application.dto.mapper;

import java.util.Objects;

import com.waterconnect.application.dto.ServicePointResultDto;
import com.waterconnect.application.dto.enums.ServicePointStatus;
import com.waterconnect.domain.model.aggregate.ServicePoint;

public class ServicePointResultDtoMapper {

    private ServicePointResultDtoMapper() {}

    public static ServicePointResultDto fromDomain(ServicePoint servicePoint) {
        if (Objects.isNull(servicePoint)) {
            return null;
        }

        var waterMeter = WaterMeterResultDtoMapper.fromDomain(servicePoint.getMeter());

        return new ServicePointResultDto(
                servicePoint.getServicePointId(),
                servicePoint.getCustomerId(),
                servicePoint.getConnectorId(),
                waterMeter,
                ServicePointStatus.fromString(servicePoint.getStatus().name()),
                servicePoint.getRequestedAt(),
                servicePoint.getActivatedAt()
        );
    }
}
