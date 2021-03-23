package com.example.beershop.models;

//This is the data model class for beers
public class BeerModel {
    int mBeerID; // Unique ID for the record
    String mBeerDescription;
    int mBeerPicturesID; // ID number for the pictures
    int mBeerCategoryID; // ID of the beer category
    int mBeerBreweryID; // ID for the brewery

    public int getBeerID() {
        return mBeerID;
    }

    public void setBeerID(int mBeerID) {
        this.mBeerID = mBeerID;
    }

    public String getBeerDescription() {
        return mBeerDescription;
    }

    public void setBeerDescription(String mBeerDescription) {
        this.mBeerDescription = mBeerDescription;
    }

    public int getBeerPicturesID() {
        return mBeerPicturesID;
    }

    public void setBeerPicturesID(int mBeerPicturesID) {
        this.mBeerPicturesID = mBeerPicturesID;
    }

    public int getBeerCategoryID() {
        return mBeerCategoryID;
    }

    public void setBeerCategoryID(int mBeerCategoryID) {
        this.mBeerCategoryID = mBeerCategoryID;
    }

    public int getBeerBreweryID() {
        return mBeerBreweryID;
    }

    public void setBeerBreweryID(int mBeerBreweryID) {
        this.mBeerBreweryID = mBeerBreweryID;
    }


}
