package com.waterconnect.application.query;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.dto.WorkOrderResultDto;
import com.waterconnect.application.dto.mapper.WorkOrderResultDtoMapper;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Service
public class WorkOrderQuery {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderQuery(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    /**
     * Get WorkOrder by provided ID
     * @return Result DTO for work order
     * @throws EntityNotFoundException in a case no WorkOrder founded
     */
    @Transactional
    public WorkOrderResultDto getById(UUID workOrderId) throws EntityNotFoundException {
        var wo = this.workOrderRepository.findById(workOrderId).orElse(null);

        if (Objects.isNull(wo)) {
            throw new EntityNotFoundException("Workorder", workOrderId);
        }

        return WorkOrderResultDtoMapper.fromDomain(wo);
    }
}
