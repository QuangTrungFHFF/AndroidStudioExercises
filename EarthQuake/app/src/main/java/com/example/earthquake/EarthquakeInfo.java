package com.example.earthquake;

public class EarthquakeInfo {

    private double mMag;
    private String mLocation;
    private int mDate;

    public EarthquakeInfo(double mag, String location, int date) {
        this.mMag = mag;
        this.mLocation = location;
        this.mDate = date;
    }

    public double getMag() {
        return mMag;
    }

    public int getDate() {
        return mDate;
    }

    public String getLocation() {
        return mLocation;
    }

    @Override
    public String toString() {
        return "EarthquakeInfo{" +
                "mag=" + mMag +
                ", location='" + mLocation + '\'' +
                ", date='" + mDate + '\'' +
                '}';
    }
}
