package com.example.beershop.models;

//This is a data model for Customers
public class CustomerModel {
    int m_customerID; // Unique ID for the record
    String m_customerUsername; // Username of the customer
    String m_customerPassword; // Password of the customer

    public CustomerModel(int m_customerID, String m_customerUsername, String m_customerPassword) {
        this.m_customerID = m_customerID;
        this.m_customerUsername = m_customerUsername;
        this.m_customerPassword = m_customerPassword;
    }

    public int getCustomerID() {
        return m_customerID;
    }

    public void setCustomerID(int m_customerID) {
        this.m_customerID = m_customerID;
    }

    public String getCustomerUsername() {
        return m_customerUsername;
    }

    public void setCustomerUsername(String m_customerUsername) {
        this.m_customerUsername = m_customerUsername;
    }

    public String getCustomerPassword() {
        return m_customerPassword;
    }

    public void setCustomerPassword(String m_customerPassword) {
        this.m_customerPassword = m_customerPassword;
    }


}
