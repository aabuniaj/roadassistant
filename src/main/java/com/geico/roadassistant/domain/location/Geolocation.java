package com.geico.roadassistant.domain.location;


import lombok.Data;

@Data
public class Geolocation {
    
    private double latitude;
    private double longitude;
    //private final GeoHash geoHash;

    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //copied from stackoverflow
    public double distanceTo(Geolocation other) {
        // Calculate the distance in miles between two geolocations using the Haversine formula
        double earthRadius = 3958.8; // miles
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
