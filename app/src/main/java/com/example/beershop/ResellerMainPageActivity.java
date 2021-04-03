package com.example.beershop;

public class ResellerMainPageActivity extends SingleFragmentActivity {
    @Override
    protected ResellerMainPageFragment createFragment() {
        return ResellerMainPageFragment.newInstance();
    }
}
