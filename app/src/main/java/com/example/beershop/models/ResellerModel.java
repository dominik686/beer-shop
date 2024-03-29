package com.example.beershop.models;

import androidx.annotation.NonNull;

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

    public String getUsername() {
        return m_Username;
    }

    public String getPassword() {
        return m_Password;
    }

    public String getInventory() {
        return m_Inventory;
    }

    public void setInventory(String mInventory) {
        this.m_Inventory = mInventory;
    }


    @NonNull
    @Override
    public String toString() {
        return "ResellerModel{" +
                "m_ID=" + m_ID +
                ", m_Username='" + m_Username + '\'' +
                ", m_Password='" + m_Password + '\'' +
                ", m_Inventory='" + m_Inventory + '\'' +
                '}';
    }
}
