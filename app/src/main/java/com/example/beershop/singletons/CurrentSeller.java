package com.example.beershop.singletons;

import android.content.Context;

import com.example.beershop.models.BasketModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.models.ResellerModel;

public class CurrentSeller {
    private static CurrentSeller sCurrentSeller;
    private static ResellerModel mResellerModel;
    private static BasketModel mBasketModel;
    private final Context mContext;


    private CurrentSeller(Context pContext) {
        mContext = pContext;
    }

    public static CurrentSeller getInstance(Context pContext, ResellerModel rm) {
        if (sCurrentSeller == null) {
            sCurrentSeller = new CurrentSeller(pContext);
        }
        mResellerModel = rm;
        mBasketModel = new BasketModel();

        return sCurrentSeller;
    }

    public static CurrentSeller getInstance(Context pContext) {
        if (sCurrentSeller == null) {
            sCurrentSeller = new CurrentSeller(pContext);
        }
        return sCurrentSeller;
    }

    public ResellerModel getResellerModel() {
        return mResellerModel;
    }

    public void addToBasket(BeerModel model) {
        mBasketModel.addBeer(model);
    }

    public BasketModel getBasketModel() {
        return mBasketModel;
    }
}

