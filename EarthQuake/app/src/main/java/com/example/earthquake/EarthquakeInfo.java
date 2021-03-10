package com.example.earthquake;

public class EarthquakeInfo {

    private double mMag;
    private String mLocation;
    private long mDate;

    public EarthquakeInfo(double mag, String location, long date) {
        this.mMag = mag;
        this.mLocation = location;
        this.mDate = date;
    }

    public double getMag() {
        return mMag;
    }

    public long getDate() {
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
