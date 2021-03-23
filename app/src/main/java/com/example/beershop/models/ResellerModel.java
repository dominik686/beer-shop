package com.example.beershop.models;

//This is the data model class for Resellers
public class ResellerModel {
    int m_ID; // Unique ID for the record
    String m_Username; // Username of the reseller
    String m_Password; // Password of the reseller
    String m_Inventory; // String containing IDs of the beers in stock

    public ResellerModel(int m_ID, String m_Username, String m_Password) {
        this.m_ID = m_ID;
        this.m_Username = m_Username;
        this.m_Password = m_Password;
        this.m_Inventory = "";
    }

    public ResellerModel(int m_ID, String m_Username, String m_Password, String m_Inventory) {
        this.m_ID = m_ID;
        this.m_Username = m_Username;
        this.m_Password = m_Password;
        this.m_Inventory = m_Inventory;
    }

    public int getID() {
        return m_ID;
    }

    public void setID(int m_ID) {
        this.m_ID = m_ID;
    }

    public String getUsername() {
        return m_Username;
    }

    public void setUsername(String mUsername) {
        this.m_Username = mUsername;
    }

    public String getPassword() {
        return m_Password;
    }

    public void setPassword(String mPassword) {
        this.m_Password = mPassword;
    }

    public String getInventory() {
        return m_Inventory;
    }

    public void setInventory(String mInventory) {
        this.m_Inventory = mInventory;
    }


}
