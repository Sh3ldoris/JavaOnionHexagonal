package com.waterconnect.application.dto.mapper;

import java.util.Objects;

import com.waterconnect.application.dto.WaterMeterResultDto;
import com.waterconnect.domain.model.entity.WaterMeter;

public class WaterMeterResultDtoMapper {
    private WaterMeterResultDtoMapper() {}

    public static WaterMeterResultDto fromDomain(WaterMeter meter) {
        if (Objects.isNull(meter)) {
            return null;
        }

        return new WaterMeterResultDto(
                meter.getSerialNumber(),
                meter.getInstalledAt(),
                meter.getLastReadingM3(),
                meter.getLastReadingAt()
        );
    }
}
