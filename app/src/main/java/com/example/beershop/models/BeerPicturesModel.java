package com.example.beershop.models;

import java.util.List;

//This is the data model class for beer pictures
//This would contain URL to pictures, but because it is a prototype it is stores names of the images
// that are stored on the device
public class BeerPicturesModel {
    int mID; // Unique ID for the record
    String mThumbnail; // Name of the thumbnail
    List<String> mImages; // List of names of images
}
