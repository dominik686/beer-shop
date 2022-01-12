package com.example.beershop.activities;

import com.example.beershop.fragments.CustomerShopPageFragment;
import com.example.beershop.fragments.ResellerAddBeerFragment;

public class CustomerShopPageActivity extends SingleFragmentActivity {


    @Override
    protected CustomerShopPageFragment createFragment() {
        return CustomerShopPageFragment.newInstance();
    }
}