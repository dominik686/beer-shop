package com.example.beershop.models;

//This is the data model class for beers
public class BeerModel {
    int mBeerID; // Unique ID for the record
    String mBeerThumbnailName; // String for the pictures
    int mBeerCategoryID; // ID of the beer category
    int mBeerBreweryID; // ID for the brewery

    public BeerModel() {

    }

    public int getBeerID() {
        return mBeerID;
    }

    public void setBeerID(int mBeerID) {
        this.mBeerID = mBeerID;
    }

    public String getBeerThumbnailName() {
        return mBeerThumbnailName;
    }

    public void setBeerThumbnailName(String mBeerThumbnailName) {
        this.mBeerThumbnailName = mBeerThumbnailName;
    }

    public void setBeerDescription(String mBeerDescription) {
    }

    public int getBeerCategoryID() {
        return mBeerCategoryID;
    }

    public int getBeerBreweryID() {
        return mBeerBreweryID;
    }

    public void setBeerBreweryID(int mBeerBreweryID) {
        this.mBeerBreweryID = mBeerBreweryID;
    }


}
