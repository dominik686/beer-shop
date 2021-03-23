package com.example.beershop;

public class CreateAccountActivity extends SingleFragmentActivity {

    @Override
    protected CreateAccountFragment createFragment() {
        return CreateAccountFragment.newInstance();
    }
}
