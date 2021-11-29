package com.example.beershop.activities;

import com.example.beershop.fragments.CustomerSellerListFragment;

public class CustomerSellerListActivity extends SingleFragmentActivity {
    @Override
    protected CustomerSellerListFragment createFragment() {
        return CustomerSellerListFragment.newInstance();
    }
}