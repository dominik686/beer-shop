package com.example.beershop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.beershop.models.BeerModel;
import com.example.beershop.models.CustomerModel;
import com.example.beershop.models.ResellerModel;

import java.util.ArrayList;
import java.util.List;

public class UserDataBaseHelper extends SQLiteOpenHelper {
    //Customers
    public static final String CUSTOMERS_TABLE = "Customers";
    public static final String COLUMN_CUSTOMER_ID = "CustomerID";
    public static final String COLUMN_CUSTOMER_USERNAME = "CustomerUsername";
    public static final String COLUMN_CUSTOMER_PASSWORD = "CustomerPassword";


    public static final String RESELLERS_TABLE = "Resellers";
    public static final String COLUMN_RESELLER_ID = "ResellerID";
    public static final String COLUMN_RESELLER_USERNAME = "ResellerUsername";
    public static final String COLUMN_RESELLER_PASSWORD = "ResellerPassword";
    public static final String COLUMN_RESELLER_INVENTORY = "ResellerInventory";

    //Resellers
    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    //This is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the customers table
        String createCustomersTableStatement = "CREATE TABLE " + CUSTOMERS_TABLE + " (" +
                "  " + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_CUSTOMER_USERNAME + " TEXT NOT NULL," +
                "  " + COLUMN_CUSTOMER_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createCustomersTableStatement);

        //Create the resellers table
        String createResellersTableStatement = "CREATE TABLE " + RESELLERS_TABLE + " (" +
                "  " + COLUMN_RESELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_RESELLER_USERNAME + " TEXT NOT NULL," +
                "  " + COLUMN_RESELLER_PASSWORD + " TEXT NOT NULL," +
                "  " + COLUMN_RESELLER_INVENTORY + " TEXT)";
        db.execSQL(createResellersTableStatement);

    }

    // this is called if the database version number changes. It prevents previous users apps from breaking when you create
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /// find customerModel in the database. If it is found, delete it and return true
    // if it is not found, return false
    public boolean deleteCustomer(CustomerModel cm) {
        // find customerModel in the database. If it is found, delete it and return true
        // if it is not found, return false
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMERS_TABLE + " WHERE " + COLUMN_CUSTOMER_ID + " = "
                + cm.getCustomerID();

        Cursor cursor = db.rawQuery(queryString, null);

        boolean res = cursor.moveToFirst();
        db.close();

        return res;

    }

    //Return true if the customerModel credentials match the database
    //else return false
    public boolean customerCredentialsCheck(CustomerModel cm) {

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CUSTOMERS_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = "
                + "\"" + cm.getCustomerUsername() + "\"" + " AND " + COLUMN_CUSTOMER_PASSWORD + " = " + "\"" + cm.getCustomerPassword() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);
        boolean res = cursor.moveToFirst();
        db.close();
        return res;
    }

    //Return true if the customerModel username matches any username in the DB
    //if it doesnt, return false
    public boolean customerUsernameCheck(CustomerModel cm) {
        //
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CUSTOMERS_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = "
                + "\"" + cm.getCustomerUsername() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        boolean res = cursor.moveToFirst();

        db.close();
        return res;
    }

    //Try to add the customer to the DB, return true if successful
    //and false if not
    public boolean addCustomer(CustomerModel cm) {
        if (customerUsernameCheck(cm)) {
            return false;
        }
        //Return true if the customer has been added sucessfully
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_USERNAME, cm.getCustomerUsername());
        cv.put(COLUMN_CUSTOMER_PASSWORD, cm.getCustomerPassword());
        //If the username alraedy exists in the db
        //dont add and return false
        long insert = db.insert(CUSTOMERS_TABLE, null, cv);
        db.close();
        return insert != -1;

    }


    //Reseller table CRUD methods
    public boolean addReseller(ResellerModel rm) {
        if (resellerUsernameCheck(rm)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RESELLER_USERNAME, rm.getUsername());
        cv.put(COLUMN_RESELLER_PASSWORD, rm.getPassword());
        cv.put(COLUMN_RESELLER_INVENTORY, "");
        long insert = db.insert(RESELLERS_TABLE, null, cv);


        return insert != -1;

    }

    //Add the beer to the resellers inventory
    public boolean addBeerToInventory(ResellerModel rm, int beerID, int quantity) {
        //If the beer already exists, just update the quantity?
        String newBeer = beerID + ":" + quantity + ",";

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "SELECT * FROM " + RESELLERS_TABLE + " WHERE " + COLUMN_RESELLER_USERNAME + " = "
                + "'" + rm.getUsername() + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        String curs = cursor.getString(3);
        String inventory = curs + newBeer;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESELLER_INVENTORY, inventory);


        long res = db.update(RESELLERS_TABLE, cv, COLUMN_RESELLER_USERNAME + "=?", new String[]{rm.getUsername()});
        db.close();
        return res == -1;

        //Check if the beer doesnt already exist
        //get the current column, and then concanete the newBeer to it, and then replace the column?
    }

    public boolean updateInventory(String inventory, ResellerModel rm) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COLUMN_RESELLER_INVENTORY, inventory);
        long res = db.update(RESELLERS_TABLE, cv, COLUMN_RESELLER_USERNAME + "=?",
                new String[]{rm.getUsername()});

        db.close();
        return res != -1;
    }

    // Update the quantity of the beer in the inventory
    public boolean updateQuantity(BeerModel bm, ResellerModel rm) {
        //
        String oldInventory = getInventory(rm);
        String newInventory = "";
        String[] beersString = oldInventory.split(",");

        //Go through all the items in the inventory
        for (String beer : beersString) {
            String id = beer.substring(0, beer.indexOf(':'));
            String quantity = beer.substring(beer.indexOf(':') + 1);

            // Modify the string with the new quantity
            if (Integer.parseInt(id) == bm.getBeerID()) {
                newInventory += bm.toItemString();

            } else {
                BeerModel beerModel = new BeerModel(Integer.parseInt(id), Integer.parseInt(quantity));
                newInventory += beerModel.toItemString();
            }
        }


        return updateInventory(newInventory, rm);
    }


    public boolean removeFromInventory(List<BeerModel> beers, ResellerModel rm) {
        // Get current inventory string
        // loop over it and modify it with beers changed values?
        // and then update the inventory
        String oldInventory = getInventory(rm);
        String[] beersString = oldInventory.split(",");
        ArrayList<BeerModel> newBeers = new ArrayList<>();
        for (String beer : beersString) {
            String id = beer.substring(0, beer.indexOf(':'));


            String quantity = beer.substring(beer.indexOf(':') + 1);

            BeerModel bm = new BeerModel(Integer.parseInt(id), Integer.parseInt(quantity));

            newBeers.add(bm);
        }

        String newInventory = "";
        for (int i = 0; i < newBeers.size(); i++) {
            BeerModel beer1 = newBeers.get(i);
            for (BeerModel beer2 : beers) {
                if (beer1.getBeerID() == beer2.getBeerID()) {
                    int beer1Quantity = beer1.getQuantity();
                    int beer2Quantity = beer2.getQuantity();

                    beer1Quantity = beer1Quantity - beer2Quantity;
                    beer1.setQuantity(beer1Quantity);
                    newBeers.set(i, beer1);
                }

            }
            // Turn newBeers into an inventory String
            newInventory += (beer1.getBeerID() + ":" + beer1.getQuantity() + ",");


            // Turn newBeers into an inventory String

        }

        // update resellers inventory
        boolean res = updateInventory(newInventory, rm);
        return res;

        // for each item in oldInventory
        //    check if any of the beers are the item
        //       If they are, remove the quantity from beers
    }

    // Check if the beer exists in the inventory, and if it does return it with added quantity of item
    public BeerModel getBeer(BeerModel bm, ResellerModel rm) {
        //check if the beer exists in the resellers inventory, and if it doesnt set the quantity to 0 ( or maybe -1?) and return the beer
        BeerModel returnModel = null;

        // If there is no inventory return null
        String inventory = getInventory(rm);

        if (TextUtils.isEmpty(inventory)) {
            return null;
        }
        String[] inventoryList = inventory.split(",");

        // Look for the item with the same ID ast
        for (String item : inventoryList) {
            if (Integer.parseInt(item.substring(0, item.indexOf(":"))) == bm.getBeerID()) {
                returnModel = bm.copy();
                // int d =Integer.parseInt( item.substring( item.indexOf(":" + 1)));
                returnModel.setQuantity(Integer.parseInt(item.substring(item.indexOf(":") + 1)));
                break;
            }
        }


        return returnModel;
    }


    // Check if the username already exists
    public boolean resellerUsernameCheck(ResellerModel rm) {

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + RESELLERS_TABLE + " WHERE " + COLUMN_RESELLER_USERNAME + " = "
                + "\"" + rm.getUsername() + "\"";
        Cursor cursor = db.rawQuery(queryString, null);
        boolean res = cursor.moveToFirst();

        return res;
    }

    //Get inventory
    public String getInventory(ResellerModel rm) {

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_RESELLER_INVENTORY + " FROM " + RESELLERS_TABLE +
                " WHERE " + COLUMN_RESELLER_USERNAME + " = "
                + "\"" + rm.getUsername() + "\"";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();

        db.close();
        return cursor.getString(0);
    }

    public List<ResellerModel> getAllResellers() {
        String query = "SELECT * FROM " + RESELLERS_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ResellerModel> returnList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        //Loop throug hthe cursor (result set) and create new reseller objects. Put them into the return list
        if (cursor.moveToFirst()) {
            do {
                int resellerID = cursor.getInt(0);
                String resellerUsername = cursor.getString(1);
                String resellerPassword = cursor.getString(2);
                String resellerInventory = cursor.getString(3);

                ResellerModel newReseller = new ResellerModel(resellerID, resellerUsername, resellerPassword
                        , resellerInventory);
                returnList.add(newReseller);
            }

            while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return returnList;
    }

    public boolean resellerCredentialsCheck(ResellerModel rm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + RESELLERS_TABLE + " WHERE " + COLUMN_RESELLER_USERNAME + " = "
                + "\"" + rm.getUsername() + "\"" + " AND " + COLUMN_RESELLER_PASSWORD + " = " + "\"" + rm.getPassword() + "\"";
        Cursor cursor = db.rawQuery(queryString, null);
        close();
        boolean res = cursor.moveToFirst();
        cursor.close();
        return res;
    }


    // Clear databases
    public void clearDbAndRecreate()
    {
        //Remove tables
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE "+CUSTOMERS_TABLE);
        db.execSQL("DROP TABLE " + RESELLERS_TABLE);

        //Recreate tables
        //Create the customers table
        String createCustomersTableStatement = "CREATE TABLE " + CUSTOMERS_TABLE + " (" +
                "  " + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_CUSTOMER_USERNAME + " TEXT NOT NULL," +
                "  " + COLUMN_CUSTOMER_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createCustomersTableStatement);

        //Create the resellers table
        String createResellersTableStatement = "CREATE TABLE " + RESELLERS_TABLE + " (" +
                "  " + COLUMN_RESELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  " + COLUMN_RESELLER_USERNAME + " TEXT NOT NULL," +
                "  " + COLUMN_RESELLER_PASSWORD + " TEXT NOT NULL," +
                "  " + COLUMN_RESELLER_INVENTORY + " TEXT)";
        db.execSQL(createResellersTableStatement);
        close();

    }
    public void clearDb()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE "+CUSTOMERS_TABLE);
        db.execSQL("DROP TABLE " + RESELLERS_TABLE);
        close();
    }
}
