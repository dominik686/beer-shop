package com.example.beershop;

import androidx.fragment.app.Fragment;

public class ResellerAddBeerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ResellerAddBeerFragment.newInstance();
    }
}
