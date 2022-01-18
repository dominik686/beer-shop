package com.example.beershop.models;

//This is the data model class for beer categories
public class BeerCategoryModel {
    int mBeerCategoryID;
    String mBeerCategoryName;
    String mBeerCategoryDescription;

    public BeerCategoryModel(int mBeerCategoryID, String mBeerCategoryName, String mBeerCategoryDescription) {
        this.mBeerCategoryID = mBeerCategoryID;
        this.mBeerCategoryName = mBeerCategoryName;
        this.mBeerCategoryDescription = mBeerCategoryDescription;
    }

    public int getBeerCategoryID() {
        return mBeerCategoryID;
    }

    public String getBeerCategoryName() {
        return mBeerCategoryName;
    }

    public String getBeerCategoryDescription() {
        return mBeerCategoryDescription;
    }


}
