package com.waterconnect.api.dto;

import jakarta.validation.constraints.Min;

public record RecordMeterReadingRequestDto(
        @Min(0) double readingM3
) {
}
