package com.waterconnect.infrastructure.persistence.entity.factory;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.domain.model.entity.WaterMeter;

public class WaterMeterFactory extends WaterMeter {

    private WaterMeterFactory() {
    }

    public static WaterMeter createWaterMeter(
            UUID id,
            String serialNumber,
            Instant installedAt,
            double lastReadingM3,
            Instant lastReadingAt
    ) {
        var waterMeter = new WaterMeterFactory();
        waterMeter.setMeterId(id);
        waterMeter.setSerialNumber(serialNumber);
        waterMeter.setInstalledAt(installedAt);
        waterMeter.setLastReadingM3(lastReadingM3);
        waterMeter.setLastReadingAt(lastReadingAt);

        return waterMeter;
    }
}
