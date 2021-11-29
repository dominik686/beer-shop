package com.example.beershop.activities;

import androidx.fragment.app.Fragment;

import com.example.beershop.fragments.CustomerScanBarcodeFragment;

public class CustomerScanBarcodeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CustomerScanBarcodeFragment.newInstance();
    }
}
