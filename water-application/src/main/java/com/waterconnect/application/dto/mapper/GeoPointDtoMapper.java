package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.GeoPointDto;
import com.waterconnect.domain.model.valueobject.GeoPoint;

public class GeoPointDtoMapper {

    // No instances of mapper class
    private GeoPointDtoMapper() {}

    public static GeoPoint mapToGeoPoint(GeoPointDto geoPointDto) {
        return new GeoPoint(geoPointDto.latitude(), geoPointDto.longitude());
    }
}
