package com.example.earthquake;

public class EarthquakeInfo {

    private double mag;
    private String location;
    private String date;

    public EarthquakeInfo(double mag, String location, String date) {
        this.mag = mag;
        this.location = location;
        this.date = date;
    }

    public double getMag() {
        return mag;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "EarthquakeInfo{" +
                "mag=" + mag +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
