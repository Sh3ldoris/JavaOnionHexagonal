package com.waterconnect.domain.model.aggregate.support;

import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.Address;
import com.waterconnect.domain.model.valueobject.ContactInfo;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.model.valueobject.GeoSegment;

public final class AggregateTestFixtures {

    private AggregateTestFixtures() {}

    public static GeoPoint geoPoint() {
        return new GeoPoint(45.0, 9.0);
    }

    public static GeoSegment geoSegment() {
        return new GeoSegment(geoPoint(), new GeoPoint(45.01, 9.01));
    }

    public static Pipe pipeWithDiameterMm(int diameterMm) {
        return Pipe.planNew(diameterMm, 10.0, 16.0, PipeMaterial.PVC, geoSegment());
    }

    public static ContactInfo contactInfo(String email) {
        var address = new Address("1 Main St", "City", "12345", "US");
        return new ContactInfo(email, "+1000000000", address);
    }
}
