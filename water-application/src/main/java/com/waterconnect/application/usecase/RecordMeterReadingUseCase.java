package com.waterconnect.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.ServicePointRepository;

/**
 * Use case: Worker want to save the current water meter state
 */
@Service
public class RecordMeterReadingUseCase {

    private final ServicePointRepository servicePointRepository;

    public RecordMeterReadingUseCase(ServicePointRepository servicePointRepository) {
        this.servicePointRepository = servicePointRepository;
    }

    @Transactional
    public void execute(UUID servicePointId, double readingM3)
            throws BusinessRuleViolationException, EntityNotFoundException {
        var servicePoint = this.servicePointRepository.findById(servicePointId).orElse(null);

        if (servicePoint == null) {
            throw new EntityNotFoundException("ServicePoint",  servicePointId);
        }

        servicePoint.recordMeterReading(readingM3);
        this.servicePointRepository.save(servicePoint);
    }
}
