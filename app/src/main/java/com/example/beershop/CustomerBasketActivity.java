package com.example.beershop;

public class CustomerBasketActivity extends SingleFragmentActivity {
    @Override
    protected CustomerBasketFragment createFragment() {
        return CustomerBasketFragment.newInstance();
    }
}
