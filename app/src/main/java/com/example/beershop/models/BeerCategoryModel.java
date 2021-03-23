package com.example.beershop.models;

//This is the data model class for beer categories
public class BeerCategoryModel {
    int mBeerCategoryID;
    String mBeerCategoryName;
    String mBeerCategoryDescription;

    public int getBeerCategoryID() {
        return mBeerCategoryID;
    }

    public void setBeerCategoryID(int mBeerCategoryID) {
        this.mBeerCategoryID = mBeerCategoryID;
    }

    public String getBeerCategoryName() {
        return mBeerCategoryName;
    }

    public void setBeerCategoryName(String mBeerCategoryName) {
        this.mBeerCategoryName = mBeerCategoryName;
    }

    public String getBeerCategoryDescription() {
        return mBeerCategoryDescription;
    }

    public void setBeerCategoryDescription(String mBeerCategoryDescription) {
        this.mBeerCategoryDescription = mBeerCategoryDescription;
    }


}
