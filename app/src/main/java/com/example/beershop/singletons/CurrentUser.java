package com.example.beershop.singletons;

import android.content.Context;

import com.example.beershop.models.CustomerModel;
import com.example.beershop.models.ResellerModel;

// This is a singleton class that stores the data model of the current user
public class CurrentUser {
    private static CurrentUser sCurrentUser = null;
    private static ResellerModel mResellerUser = null;
    private static CustomerModel mCustomerUser = null;


    private CurrentUser() {

    }

    //If the resseller is the current user
    public static CurrentUser getInstance(ResellerModel rm) {
        if (sCurrentUser == null) {
            sCurrentUser = new CurrentUser();
        }

        mResellerUser = rm;
        return sCurrentUser;
    }

    public static CurrentUser getInstance(CustomerModel cm) {
        if (sCurrentUser == null) {
            sCurrentUser = new CurrentUser();
        }

        mCustomerUser = cm;
        return sCurrentUser;
    }

    public static CurrentUser getInstance() {
        if (sCurrentUser == null) {
            sCurrentUser = new CurrentUser();
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