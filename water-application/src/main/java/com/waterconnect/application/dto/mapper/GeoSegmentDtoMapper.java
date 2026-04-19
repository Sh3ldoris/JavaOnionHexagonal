package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.GeoSegmentDto;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.model.valueobject.GeoSegment;

public class GeoSegmentDtoMapper {

    // No instances of mapper class
    private GeoSegmentDtoMapper() {}

    public static GeoSegment toDomain(GeoSegmentDto geoSegmentDto) {
        GeoPoint startPoint = GeoPointDtoMapper.toDomain(geoSegmentDto.start());
        GeoPoint endPoint = GeoPointDtoMapper.toDomain(geoSegmentDto.end());

        return new GeoSegment(startPoint, endPoint);
    }

    public static GeoSegmentDto fromDomain(GeoSegment geoSegment) {
        var startDto = GeoPointDtoMapper.fromDomain(geoSegment.start());
        var endDto = GeoPointDtoMapper.fromDomain(geoSegment.end());
        return new GeoSegmentDto(startDto, endDto);
    }
}
