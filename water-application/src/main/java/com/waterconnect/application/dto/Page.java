package com.waterconnect.application.dto;

import java.util.List;

public record Page<T>(
        int pageNumber,
        int pageSize,
        List<T> items
) {
}
