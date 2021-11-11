package com.example.beershop.singletons;

import android.content.Context;

import com.example.beershop.models.BasketModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.models.ResellerModel;

public class CurrentSeller {
    private static CurrentSeller sCurrentSeller;
    private static ResellerModel mResellerModel;
    private static BasketModel mBasketModel;


    private CurrentSeller() {

    }

    public static CurrentSeller getInstance(ResellerModel rm) {
        if (sCurrentSeller == null) {
            sCurrentSeller = new CurrentSeller();
        }
        mResellerModel = rm;
        mBasketModel = new BasketModel();

        return sCurrentSeller;
    }

    public static CurrentSeller getInstance() {
        if (sCurrentSeller == null) {
            sCurrentSeller = new CurrentSeller();
        }
        return sCurrentSeller;
    }

    public ResellerModel getResellerModel() {
        return mResellerModel;
    }

    public void clearBasket() {
        mBasketModel.clear();
    }

    public void updateInventory(String inventory) {
        mResellerModel.setInventory(inventory);
    }

    public void addToBasket(BeerModel model) {
        mBasketModel.addBeer(model);
    }

    public BasketModel getBasketModel() {
        return mBasketModel;
    }
}

