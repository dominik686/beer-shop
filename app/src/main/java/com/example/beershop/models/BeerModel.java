package com.example.beershop.models;

//This is the data model class for beers
public class BeerModel {
    int mBeerID; // Unique ID for the record
    String mBeerName;
    String mBeerThumbnailName; // String for the pictures
    String mBarcode; //Barcode of the beer
    int mBeerCategoryID; // ID of the beer category
    int mBeerBreweryID; // ID for the brewery

    public BeerModel(int mBeerID, String mBeerName, String mBeerThumbnailName, String mBarcode, int mBeerCategoryID, int mBeerBreweryID) {
        this.mBeerID = mBeerID;
        this.mBeerName = mBeerName;
        this.mBeerThumbnailName = mBeerThumbnailName;
        this.mBarcode = mBarcode;
        this.mBeerCategoryID = mBeerCategoryID;
        this.mBeerBreweryID = mBeerBreweryID;
    }

    public BeerModel() {

    }

    public String getBarcode() {
        return mBarcode;
    }

    public void setBarcode(String mBarcode) {
        this.mBarcode = mBarcode;
    }

    public String getBeerName() {
        return mBeerName;
    }

    public void setBeerName(String mBeerName) {
        this.mBeerName = mBeerName;
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
