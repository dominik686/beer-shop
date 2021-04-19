package com.example.beershop;

import androidx.fragment.app.Fragment;

public class ResellerScanBarcodeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ResellerScanBarcodeFragment.newInstance();
    }
}
