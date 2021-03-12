package com.example.earthquake;

public class EarthquakeInfo {

    private double mMag;
    private String mLocation;
    private long mDate;
    private String mUrl;

    public EarthquakeInfo(double mag, String location, long date, String mUrl) {
        this.mMag = mag;
        this.mLocation = location;
        this.mDate = date;
        this.mUrl = mUrl;
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

    public String getmUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return "EarthquakeInfo{" +
                "mMag=" + mMag +
                ", mLocation='" + mLocation + '\'' +
                ", mDate=" + mDate +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
