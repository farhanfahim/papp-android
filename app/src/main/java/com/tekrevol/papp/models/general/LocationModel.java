package com.tekrevol.papp.models.general;

/**
 * Created by aqsa.sarwar on 2/21/2018.
 */

public class LocationModel {
    private double lat;
    private double lng;
    private String address;


    public LocationModel(double lat, double lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
