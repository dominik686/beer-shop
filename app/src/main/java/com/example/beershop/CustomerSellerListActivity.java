package com.example.beershop;

public class CustomerSellerListActivity extends SingleFragmentActivity {
    @Override
    protected CustomerSellerListFragment createFragment() {
        return CustomerSellerListFragment.newInstance();
    }
}