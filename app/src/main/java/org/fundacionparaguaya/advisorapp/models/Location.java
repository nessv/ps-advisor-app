package org.fundacionparaguaya.advisorapp.models;

/**
 * A geographical location.
 */

public class Location {
    private double longitude;
    private double latitude;
    private Country country;
    private City city;

    public Location(double longitude, double latitude, Country country, City city) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.city = city;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Country getCountry() {
        return country;
    }

    public City getCity() {
        return city;
    }
}
