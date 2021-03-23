package com.example.beershop.models;

//This is a data model class for Breweries
public class BeerBreweryModel {
    int mBeerBreweryID; // Unique ID for the record
    String mBeerBreweryName; // Name of the brewery
    String mBeerBreweryDescription; // Description of the brewery

    public int getBeerBreweryID() {
        return mBeerBreweryID;
    }

    public void setBeerBreweryID(int mBeerBreweryID) {
        this.mBeerBreweryID = mBeerBreweryID;
    }

    public String getBeerBreweryName() {
        return mBeerBreweryName;
    }

    public void setBeerBreweryName(String mBeerBreweryName) {
        this.mBeerBreweryName = mBeerBreweryName;
    }

    public String getBeerBreweryDescription() {
        return mBeerBreweryDescription;
    }

    public void setBeerBreweryDescription(String mBeerBreweryDescription) {
        this.mBeerBreweryDescription = mBeerBreweryDescription;
    }

}
