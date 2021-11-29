package com.example.beershop.activities;

import androidx.fragment.app.Fragment;

import com.example.beershop.fragments.ResellerScanBarcodeFragment;

public class ResellerScanBarcodeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ResellerScanBarcodeFragment.newInstance();
    }
}
