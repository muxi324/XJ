package com.wp.entity.map;

public class MapPoint {
    private String longitude;
    private String latitude;

    public MapPoint() {

    }

    public MapPoint(String lon, String la) {
        this.longitude = lon;
        this.latitude = la;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
