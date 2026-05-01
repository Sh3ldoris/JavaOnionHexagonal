package com.waterconnect.infrastructure.persistence.entity.factory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.ServicePoint;
import com.waterconnect.domain.model.enums.ServicePointStatus;
import com.waterconnect.infrastructure.persistence.entity.WaterMeterJpaEntity;

public class ServicePointFactory extends ServicePoint {

    private ServicePointFactory() {
    }

    public static ServicePoint createServicePoint(
            UUID servicePointId,
            UUID customerId,
            UUID connectorId,
            WaterMeterJpaEntity meter,
            ServicePointStatus status,
            Instant requestedAt,
            Instant activatedAt
    ) {
        var servicePoint = new ServicePointFactory();

        servicePoint.setServicePointId(servicePointId);
        servicePoint.setCustomerId(customerId);
        servicePoint.setConnectorId(connectorId);
        servicePoint.setMeter(
                Objects.nonNull(meter)
                        ? meter.toDomain()
                        : null
        );
        servicePoint.setStatus(status);
        servicePoint.setRequestedAt(requestedAt);
        servicePoint.setActivatedAt(activatedAt);

        return servicePoint;
    }
}
