package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public boolean isLocationTrusted(Double trustedLatitude, Double trustedLongitude, Double currentLatitude, Double currentLongitude) {
        double threshold = 0.01; // Допустимая погрешность
        if (trustedLatitude != null && trustedLongitude != null) {
            double latDiff = Math.abs(trustedLatitude - currentLatitude);
            double lonDiff = Math.abs(trustedLongitude - currentLongitude);
            return latDiff <= threshold && lonDiff <= threshold;
        }
        return false;
    }

    public boolean isDeviceTrusted(String trustedDeviceId, String currentDeviceId) {
        return trustedDeviceId != null && trustedDeviceId.equals(currentDeviceId);
    }
}
