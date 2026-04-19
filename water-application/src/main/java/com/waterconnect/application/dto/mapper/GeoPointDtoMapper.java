package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.GeoPointDto;
import com.waterconnect.domain.model.valueobject.GeoPoint;

public class GeoPointDtoMapper {

    // No instances of mapper class
    private GeoPointDtoMapper() {}

    public static GeoPoint toDomain(GeoPointDto geoPointDto) {
        return new GeoPoint(geoPointDto.latitude(), geoPointDto.longitude());
    }

    public static GeoPointDto fromDomain(GeoPoint geoPoint) {
        return new GeoPointDto(geoPoint.latitude(), geoPoint.longitude());
    }
}
