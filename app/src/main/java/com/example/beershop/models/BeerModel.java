package com.example.beershop.models;

//This is the data model class for beers
public class BeerModel {
    private int mBeerID; // Unique ID for the record
    private String mBeerName;
    private String mBeerThumbnailName; // String for the pictures
    private String mBarcode; //Barcode of the beer
    private int mBeerCategoryID; // ID of the beer category
    private int mBeerBreweryID; // ID for the brewery
    private int mBeerQuantity = 1;

    public BeerModel(int mBeerID, String mBeerName, String mBeerThumbnailName, int mBeerCategoryID, int mBeerBreweryID, String mBarcode) {
        this.mBeerID = mBeerID;
        this.mBeerName = mBeerName;
        this.mBeerThumbnailName = mBeerThumbnailName;
        this.mBarcode = mBarcode;
        this.mBeerCategoryID = mBeerCategoryID;
        this.mBeerBreweryID = mBeerBreweryID;
    }

    public BeerModel(int mBeerID, int mBeerQuantity) {
        this.mBeerID = mBeerID;
        this.mBeerQuantity = mBeerQuantity;
    }

    public BeerModel() {

    }

    public void addQuantity(int number) {
        mBeerQuantity += number;
    }

    public int getQuantity() {
        return mBeerQuantity;
    }

    public void setQuantity(int quantity) {
        mBeerQuantity = quantity;
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

    public BeerModel copy() {
        return new BeerModel(mBeerID, mBeerName, mBeerThumbnailName, mBeerCategoryID, getBeerBreweryID(), mBarcode);
    }

    public String toItemString() {
        return getBeerID() + ":" + getQuantity() + ",";
    }
}
