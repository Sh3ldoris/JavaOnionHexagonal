package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.GeoSegmentDto;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.model.valueobject.GeoSegment;

public class GeoSegmentDtoMapper {

    // No instances of mapper class
    private GeoSegmentDtoMapper() {}

    public static GeoSegment mapToGeoPoint(GeoSegmentDto geoSegmentDto) {
        GeoPoint startPoint = GeoPointDtoMapper.mapToGeoPoint(geoSegmentDto.start());
        GeoPoint endPoint = GeoPointDtoMapper.mapToGeoPoint(geoSegmentDto.end());

        return new GeoSegment(startPoint, endPoint);
    }
}
