package com.example.beershop;

public class CustomerBrowseAllActivity extends SingleFragmentActivity {
    @Override
    protected CustomerBrowseAllFragment createFragment() {
        return CustomerBrowseAllFragment.newInstance();
    }
}
