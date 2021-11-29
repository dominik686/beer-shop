package com.example.beershop.activities;

import com.example.beershop.fragments.ResellerAddBeerFragment;

public class CustomerShopPageActivity extends SingleFragmentActivity {


    @Override
    protected ResellerAddBeerFragment.CustomerShopPageFragment createFragment() {
        return ResellerAddBeerFragment.CustomerShopPageFragment.newInstance();
    }
}