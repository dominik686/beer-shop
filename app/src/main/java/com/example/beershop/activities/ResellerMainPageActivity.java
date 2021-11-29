package com.example.beershop.activities;

import com.example.beershop.fragments.ResellerMainPageFragment;

public class ResellerMainPageActivity extends SingleFragmentActivity {
    @Override
    protected ResellerMainPageFragment createFragment() {
        return ResellerMainPageFragment.newInstance();
    }
}
