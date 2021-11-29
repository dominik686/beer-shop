package com.example.beershop.activities;

import com.example.beershop.fragments.CustomerBasketFragment;

public class CustomerBasketActivity extends SingleFragmentActivity {
    @Override
    protected CustomerBasketFragment createFragment() {
        return CustomerBasketFragment.newInstance();
    }
}
