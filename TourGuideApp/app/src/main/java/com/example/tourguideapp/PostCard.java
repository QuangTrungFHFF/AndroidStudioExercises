package com.example.tourguideapp;

public class PostCard {

    private int mImageId;
    private String mCityName;
    private String mLandscapeName;
    private int mPrice;

    public PostCard(int mImageId, String mCityName, String mLandscapeName, int mPrice) {
        this.mImageId = mImageId;
        this.mCityName = mCityName;
        this.mLandscapeName = mLandscapeName;
        this.mPrice = mPrice;
    }

    public int getmImageId() {
        return mImageId;
    }

    public String getmCityName() {
        return mCityName;
    }

    public String getmLandscapeName() {
        return mLandscapeName;
    }

    public int getmPrice() {
        return mPrice;
    }

    @Override
    public String toString() {
        return "PostCard{" +
                "mImageId=" + mImageId +
                ", mCityName='" + mCityName + '\'' +
                ", mLandscapeName='" + mLandscapeName + '\'' +
                ", mPrice=" + mPrice +
                '}';
    }
}
