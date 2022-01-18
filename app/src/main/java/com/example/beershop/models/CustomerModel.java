package com.example.beershop.models;

import androidx.annotation.NonNull;

//This is a data model for Customers
public class CustomerModel {
    int m_ID; // Unique ID for the record
    String m_Username; // Username of the customer
    String m_Password; // Password of the customer

    public CustomerModel(int m_customerID, String m_customerUsername, String m_customerPassword) {
        this.m_ID = m_customerID;
        this.m_Username = m_customerUsername;
        this.m_Password = m_customerPassword;
    }

    public int getCustomerID() {
        return m_ID;
    }

    public String getCustomerUsername() {
        return m_Username;
    }

    public String getCustomerPassword() {
        return m_Password;
    }


    @NonNull
    @Override
    public String toString() {
        return "CustomerModel{" +
                "m_customerID=" + m_ID +
                ", m_customerUsername='" + m_Username + '\'' +
                ", m_customerPassword='" + m_Password + '\'' +
                '}';
    }
}
