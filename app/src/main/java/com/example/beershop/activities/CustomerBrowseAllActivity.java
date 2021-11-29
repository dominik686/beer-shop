package com.example.beershop.activities;

import com.example.beershop.fragments.CustomerBrowseAllFragment;

public class CustomerBrowseAllActivity extends SingleFragmentActivity {
    @Override
    protected CustomerBrowseAllFragment createFragment() {
        return CustomerBrowseAllFragment.newInstance();
    }
}
