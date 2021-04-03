package com.example.beershop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        return cursor.moveToFirst();

    }

    //Return true if the customerModel credentials match the database
    //else return false
    public boolean customerCredentialsCheck(CustomerModel cm) {

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CUSTOMERS_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = "
                + "\"" + cm.getCustomerUsername() + "\"" + " AND " + COLUMN_CUSTOMER_PASSWORD + " = " + "\"" + cm.getCustomerPassword() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    //Return true if the customerModel username matches any username in the DB
    //if it doesnt, return false
    public boolean customerUsernameCheck(CustomerModel cm) {
        //
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CUSTOMERS_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = "
                + "\"" + cm.getCustomerUsername() + "\"";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
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
        long insert = db.insert(RESELLERS_TABLE, null, cv);

        return insert != -1;

    }

    public boolean resellerUsernameCheck(ResellerModel rm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + RESELLERS_TABLE + " WHERE " + COLUMN_RESELLER_USERNAME + " = "
                + "\"" + rm.getUsername() + "\"";
        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
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
                String resellerInventory = "";

                ResellerModel newReseller = new ResellerModel(resellerID, resellerUsername, resellerPassword
                        , resellerInventory);
                returnList.add(newReseller);
            }

            while (cursor.moveToNext());
        }

        return returnList;
    }

    public boolean resellerCredentialsCheck(ResellerModel rm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + RESELLERS_TABLE + " WHERE " + COLUMN_RESELLER_USERNAME + " = "
                + "\"" + rm.getUsername() + "\"" + " AND " + COLUMN_RESELLER_PASSWORD + " = " + "\"" + rm.getPassword() + "\"";
        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }
}
