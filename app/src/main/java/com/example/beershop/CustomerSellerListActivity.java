package com.example.beershop;

import androidx.fragment.app.Fragment;

public class CustomerSellerListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return CustomerSellerListFragment.newInstance();
    }
}