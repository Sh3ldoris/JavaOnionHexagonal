package com.waterconnect.application.command;

import com.waterconnect.application.dto.GeoSegmentDto;

public record CreatePipeCommand(int diameterMm, double lengthMeters, double pressureRatingBar,
                                String pipeMaterial, GeoSegmentDto location) {
}

