package com.example.beershop;

import androidx.fragment.app.Fragment;

public class CustomerScanBarcodeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CustomerScanBarcodeFragment.newInstance();
    }
}
