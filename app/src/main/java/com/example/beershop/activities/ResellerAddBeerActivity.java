package com.example.beershop.activities;

import androidx.fragment.app.Fragment;

import com.example.beershop.fragments.ResellerAddBeerFragment;

public class ResellerAddBeerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ResellerAddBeerFragment.newInstance();
    }
}
