package com.example.beershop.models;

import java.util.ArrayList;
import java.util.List;

public class BasketModel {
    List<BeerModel> mBeers;

    public BasketModel() {
        mBeers = new ArrayList<>();
    }

    public List<BeerModel> getBeers() {
        return mBeers;
    }

    //If a beer already exists in the list, instead of adding simply increase the quantity by 1
    public void addBeer(BeerModel model) {

        for (int i = 0; i < mBeers.size(); i++) {
            BeerModel bm = mBeers.get(i);
            if (model.getBarcode() == bm.getBarcode() && model.getBeerName() == bm.getBeerName()) {
                bm.addQuantity(1);
                mBeers.set(i, bm);
                return;
            }
        }

        mBeers.add(model);

    }

    public void removeAt(int pos) {
        mBeers.remove(pos);
    }

    public void clear() {
        mBeers.clear();
    }



}
