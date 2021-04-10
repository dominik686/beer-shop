package com.example.beershop.singletons;

import android.content.Context;

import com.example.beershop.models.CustomerModel;
import com.example.beershop.models.ResellerModel;

// This is a singleton class that stores the data model of the current user
public class CurrentUser {
    private static CurrentUser sCurrentUser = null;
    private static ResellerModel mResellerUser = null;
    private static CustomerModel mCustomerUser = null;
    private final Context mApplicationContext;
    private String mUser;

    private CurrentUser(Context pApplicationContext) {
        mApplicationContext = pApplicationContext;
    }

    //If the resseller is the current user
    public static CurrentUser getInstance(Context pContext, ResellerModel rm) {
        if (sCurrentUser == null) {
            sCurrentUser = new CurrentUser(pContext);
        }

        mResellerUser = rm;
        return sCurrentUser;
    }

    public static CurrentUser getInstance(Context pContext, CustomerModel cm) {
        if (sCurrentUser == null) {
            sCurrentUser = new CurrentUser(pContext);
        }

        mCustomerUser = cm;
        return sCurrentUser;
    }

    public static CurrentUser getInstance(Context pContext) {
        if (sCurrentUser == null) {
            sCurrentUser = new CurrentUser(pContext);
        }
        return sCurrentUser;
    }

    public ResellerModel getResellerModel() {
        return mResellerUser;
    }

    public CustomerModel getCustomerModel() {
        return mCustomerUser;
    }

}

//Create an instance in the login screen?