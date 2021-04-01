package com.example.beershop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;

public class BeerDataBaseHelper extends SQLiteOpenHelper {


    public static final String BEER_BREWERY_TABLE = "BeerBrewery";
    public static final String COLUMN_BEER_BREWERY_NAME = "BeerBreweryName";
    public static final String COLUMN_BEER_BREWERY_DESCRIPTION = "BeerBreweryDescription";

    public BeerDataBaseHelper(@Nullable Context context) {
        super(context, "beer.db", null, 1);
    }

    //Beer categories table variables
    public static final String BEER_CATEGORIES_TABLE = "BeerCategories";
    public static final String COLUMN_BEER_CATEGORY_NAME = "BeerCategoryName";
    public static final String COLUMN_BEER_CATEGORY_DESCRIPTION = "BeerCategoryDescription";
    //Beer table variables
    private static final String BEERS_TABLE = "Beer";
    private static final String COLUMN_BEER_ID = "BeerID";
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
                "  " + COLUMN_BEER_PICTURE + " TEXT NOT NULL," +
                "  " + COLUMN_BEER_CATEGORY_ID + " INTEGER NOT NULL," +
                "  " + COLUMN_BEER_BREWERY_ID + " INTEGER NOT NULL," +
                "  " + COLUMN_BEER_BARCODE + " INTEGER NOT NULL) ";
        db.execSQL(createBeerTable);

        String createBeerCategories = "CREATE TABLE " + BEER_CATEGORIES_TABLE + "(" +
                "  " + COLUMN_BEER_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_BEER_CATEGORY_NAME + "TEXT NOT NULL," +
                "  " + COLUMN_BEER_CATEGORY_DESCRIPTION + "TEXT NOT NULL) ";
        db.execSQL(createBeerCategories);
        String createBeerBrewery = "CREATE TABLE " + BEER_BREWERY_TABLE + "(" +
                "  " + COLUMN_BEER_BREWERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_BEER_BREWERY_NAME + " TEXT NOT NULL," +
                "  " + COLUMN_BEER_BREWERY_DESCRIPTION + " TEXT NOT NULL)";
        db.execSQL(createBeerBrewery);


        //Add default values for all the tables

        //First add breweries
        BeerBreweryModel brewery1 = new BeerBreweryModel(-1, "Kompania Piwowarska S.A.",
                "This is a Polish " +
                        "brewing group. Kompania Piwowarska currently has three breweries: Lech Browary Wielkopolski in Poznań, " +
                        "Tyskie Browary Książęce in Tychy and Browar Dojlidy in Białystok.");
        BeerBreweryModel brewery2 = new BeerBreweryModel(-1, "Stary Browar Kościerzyna", "Polish brewery founded in Kościerzyna that dates back to 1856.");
        BeerBreweryModel brewery3 = new BeerBreweryModel(-1, "Unavailable", "The name brewery is unavaiable. Sorry for the inconvience.");
        addBrewery(brewery1);
        addBrewery(brewery2);

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

        addBeerCategory(lager);
        addBeerCategory(schwarzbier);
        addBeerCategory(ipa);
        addBeerCategory(pils);
        addBeerCategory(wheatbeer);


    }



    /* Methods for the categories table */

    public boolean addBeerCategory(BeerCategoryModel model) {
        // Check if the category doesnt already exist
        if (categoryCheck(model)) {
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


    public boolean categoryCheck(BeerCategoryModel model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + BEER_CATEGORIES_TABLE + " WHERE " + COLUMN_BEER_CATEGORY_NAME + " = "
                + "\"" + model.getBeerCategoryName() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    /* Methods for the breweries table */


    //Return true if brewery added suscessfuly
    //otherwise return false
    public boolean addBrewery(BeerBreweryModel model) {
        //Check if the brewery doesnt already exist
        if (breweryCheck(model)) {
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

    public boolean breweryCheck(BeerBreweryModel model) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + BEER_BREWERY_TABLE + " WHERE " + COLUMN_BEER_BREWERY_NAME + " = "
                + "\"" + model.getBeerBreweryName() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void debug() {
        getReadableDatabase();
    }
}
