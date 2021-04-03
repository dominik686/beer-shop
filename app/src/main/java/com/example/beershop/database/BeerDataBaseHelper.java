package com.example.beershop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;
import com.example.beershop.models.BeerModel;

import java.util.ArrayList;
import java.util.List;

public class BeerDataBaseHelper extends SQLiteOpenHelper {

    public static final String BEER_BREWERY_TABLE = "BeerBrewery";
    public static final String COLUMN_BEER_BREWERY_NAME = "BeerBreweryName";
    public static final String COLUMN_BEER_BREWERY_DESCRIPTION = "BeerBreweryDescription";

    public static final String COLUMN_BEER_NAME = "BeerName";

    //Beer categories table variables
    public static final String BEER_CATEGORIES_TABLE = "BeerCategories";
    public static final String COLUMN_BEER_CATEGORY_NAME = "BeerCategoryName";
    public static final String COLUMN_BEER_CATEGORY_DESCRIPTION = "BeerCategoryDescription";
    //Beer table variables
    private static final String BEERS_TABLE = "Beer";
    private static final String COLUMN_BEER_ID = "BeerID";

    public BeerDataBaseHelper(@Nullable Context context) {
        super(context, "beer.db", null, 1);
        getReadableDatabase();
    }

    private static final String COLUMN_BEER_PICTURE = "BeerPicture";
    private static final String COLUMN_BEER_CATEGORY_ID = "BeerCategoryID";
    private static final String COLUMN_BEER_BREWERY_ID = "BeerBreweryID";
    private static final String COLUMN_BEER_BARCODE = "BeerBarcode";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables here
        //Beers database
        String createBeerTable = "CREATE TABLE " + BEERS_TABLE + " (" +
                "  " + COLUMN_BEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_BEER_NAME + " TEXT NOT NULL," +
                "  " + COLUMN_BEER_PICTURE + " TEXT NOT NULL," +
                "  " + COLUMN_BEER_CATEGORY_ID + " INTEGER NOT NULL," +
                "  " + COLUMN_BEER_BREWERY_ID + " INTEGER NOT NULL," +
                "  " + COLUMN_BEER_BARCODE + " TEXT NOT NULL) ";
        db.execSQL(createBeerTable);

        String createBeerCategories = "CREATE TABLE " + BEER_CATEGORIES_TABLE + "(" +
                "  " + COLUMN_BEER_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_BEER_CATEGORY_NAME + " TEXT NOT NULL," +
                "  " + COLUMN_BEER_CATEGORY_DESCRIPTION + " TEXT NOT NULL) ";
        db.execSQL(createBeerCategories);
        String createBeerBrewery = "CREATE TABLE " + BEER_BREWERY_TABLE + "(" +
                "  " + COLUMN_BEER_BREWERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_BEER_BREWERY_NAME + " TEXT NOT NULL," +
                "  " + COLUMN_BEER_BREWERY_DESCRIPTION + " TEXT NOT NULL)";
        db.execSQL(createBeerBrewery);


    }


    //Add default values for all the tables
    public boolean addDefaultValues() {

        BeerBreweryModel brewery1 = new BeerBreweryModel(-1, "Kompania Piwowarska S.A.",
                "This is a Polish " +
                        "brewing group. Kompania Piwowarska currently has three breweries: Lech Browary Wielkopolski in Poznań, " +
                        "Tyskie Browary Książęce in Tychy and Browar Dojlidy in Białystok.");
        //If the brewery already exists then the values have already been added - no need to do anything
        if (checkIfBreweryExists(brewery1)) {
            return false;
        }
        //First add breweries
        BeerBreweryModel brewery2 = new BeerBreweryModel(-1, "Stary Browar Kościerzyna", "Polish brewery founded in Kościerzyna that dates back to 1856.");
        BeerBreweryModel brewery3 = new BeerBreweryModel(-1, "Unavailable", "The name brewery is unavaiable. Sorry for the inconvience.");
        addBrewery(brewery1);
        addBrewery(brewery2);
        addBrewery(brewery3);

        //Then add categories
        BeerCategoryModel lager = new BeerCategoryModel(-1, "Lager",
                "Lager is a type of beer conditioned at low temperature." +
                        " Lagers can be pale, amber, or dark. Pale lager is the most widely consumed " +
                        "and commercially available style of beer.");
        BeerCategoryModel schwarzbier = new BeerCategoryModel(-1, "Schwarzbier",
                "Schwarzbier, or black beer, is a dark lager that originated in Germany." +
                        " They tend to have an opaque, black colour with hints of chocolate or coffee flavours, and are generally around 5% ABV." +
                        " They are similar to stout in that they are made from roasted malt, which gives them their dark colour.");
        BeerCategoryModel ipa = new BeerCategoryModel(-1, "India Pale Ale",
                "India pale ale (IPA) is a hoppy beer style within the broader category of pale ale.");
        BeerCategoryModel pils = new BeerCategoryModel(-1, "Pilsner (Pils)",
                "Pilsner (also pilsener or simply pils) is a type of pale lager." +
                        " It takes its name from the Bohemian city of Pilsen (Plzeň), " +
                        "where it was first produced in 1842 by Bavarian brewer Josef Groll.");
        BeerCategoryModel wheatbeer = new BeerCategoryModel(-1, "Weizen/Weissbier",
                "Weizen/Weissbier is a top-fermented beer which is brewed with a " +
                        "large proportion of wheat relative to the amount of malted barley. Also called wheat beer.");
        BeerCategoryModel unknown = new BeerCategoryModel(-1, "Unknown",
                "The category of the beer is unknown");

        addBeerCategory(lager);
        addBeerCategory(schwarzbier);
        addBeerCategory(ipa);
        addBeerCategory(pils);
        addBeerCategory(wheatbeer);
        addBeerCategory(unknown);
        return true;
    }
    /* Methods for the categories table */

    public List<BeerCategoryModel> getAllCategories() {
        String query = "SELECT * FROM " + BEER_CATEGORIES_TABLE;
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BeerCategoryModel> returnList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        //Loop throug hthe cursor (result set) and create new reseller objects. Put them into the return list
        if (cursor.moveToFirst()) {
            do {
                int categoryID = cursor.getInt(0);
                String categoryName = cursor.getString(1);
                String categoryDescription = cursor.getString(2);

                BeerCategoryModel model = new BeerCategoryModel(categoryID, categoryName, categoryDescription);
                returnList.add(model);
            }

            while (cursor.moveToNext());
        }

        return returnList;

    }

    // Look for the category with the same name and return its id (if it exists)
    public int getIdFromCategoryName(String categoryName) {
        /*
        int resultID = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + "*" + " FROM " + BEER_CATEGORIES_TABLE +
                " WHERE " + COLUMN_BEER_CATEGORY_NAME + " = " + "\"" + categoryName + "\"";
        Cursor cursor = db.rawQuery(queryString, null);
        resultID = Integer.parseInt(cursor.getString(0));
        return  resultID;

         */
        return 0;
    }

    // Add a new beer category (if it doesnt already exist)
    public boolean addBeerCategory(BeerCategoryModel model) {
        // Check if the category doesnt already exist
        if (checkIfCategoryExists(model)) {
            return false;
        }
        //Return true if the customer has been added sucessfully
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BEER_CATEGORY_NAME, model.getBeerCategoryName());
        cv.put(COLUMN_BEER_CATEGORY_DESCRIPTION, model.getBeerCategoryDescription());
        //If the username alraedy exists in the db
        //dont add and return false
        long insert = db.insert(BEER_CATEGORIES_TABLE, null, cv);

        return insert != -1;
    }
    //If it doesnt, proceed with adding the category


    //Check if a cetegory doesnt already exist
    public boolean checkIfCategoryExists(BeerCategoryModel model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + BEER_CATEGORIES_TABLE + " WHERE " + COLUMN_BEER_CATEGORY_NAME + " = "
                + "\"" + model.getBeerCategoryName() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    /* Methods for the breweries table */

    //Return a list of brewery models from the database
    public List<BeerBreweryModel> getAllBreweries() {
        String query = "SELECT * FROM " + BEER_BREWERY_TABLE;
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BeerBreweryModel> returnList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        //Loop throug hthe cursor (result set) and create new reseller objects. Put them into the return list
        if (cursor.moveToFirst()) {
            do {
                int breweryID = cursor.getInt(0);
                String breweryName = cursor.getString(1);
                String breweryDescription = cursor.getString(2);

                BeerBreweryModel model = new BeerBreweryModel(breweryID, breweryName, breweryDescription);
                returnList.add(model);
            }

            while (cursor.moveToNext());
        }

        return returnList;

    }

    //Return true if brewery added suscessfuly
    //otherwise return false
    public boolean addBrewery(BeerBreweryModel model) {
        //Check if the brewery doesnt already exist
        if (checkIfBreweryExists(model)) {
            return false;
        }
        //If it doesnt, proceed with adding the brewery

        //Return true if the customer has been added sucessfully
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BEER_BREWERY_NAME, model.getBeerBreweryName());
        cv.put(COLUMN_BEER_BREWERY_DESCRIPTION, model.getBeerBreweryDescription());
        //If the username alraedy exists in the db
        //dont add and return false
        long insert = db.insert(BEER_BREWERY_TABLE, null, cv);

        return insert != -1;
    }

    // Check if the browery doesnt already exist
    public boolean checkIfBreweryExists(BeerBreweryModel bm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + BEER_BREWERY_TABLE + " WHERE " + COLUMN_BEER_BREWERY_NAME + " = "
                + "\"" + bm.getBeerBreweryName() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }




    /* Methods for the beer table */

    // Add a beer if it doesnt already exist
    public boolean addBeer(BeerModel model) {
        if (!checkIfBeerExists(model)) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        String queryString = "";

        return true;
    }

    // Check if the beer doesnt already exist
    public boolean checkIfBeerExists(BeerModel model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + BEERS_TABLE + " WHERE " + COLUMN_BEER_ID + " = "
                + "\"" + model.getBeerID() + " AND " + COLUMN_BEER_NAME + " = "
                + "\"" + model.getBeerName() + " AND " + COLUMN_BEER_PICTURE + " = "
                + "\"" + model.getBeerThumbnailName() + " AND " + COLUMN_BEER_BARCODE + " = " + model.getBarcode() +
                " AND " + COLUMN_BEER_CATEGORY_ID + " = "
                + "\"" + model.getBeerCategoryID() + " AND " + COLUMN_BEER_BREWERY_ID + " = "
                + "\"" + model.getBeerID() + "\"";
        Cursor cursor = db.rawQuery(queryString, null);
        String d = " AND " + COLUMN_BEER_BARCODE + " = " + model.getBarcode();
        return cursor.moveToFirst();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void debug() {
        getReadableDatabase();
    }
}