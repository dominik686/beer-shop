package com.example.beershop;

public class CustomerShopPageActivity extends SingleFragmentActivity {


    @Override
    protected CustomerShopPageFragment createFragment() {
        return CustomerShopPageFragment.newInstance();
    }
}