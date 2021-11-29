package com.example.beershop.activities;

import com.example.beershop.fragments.CreateAccountFragment;

public class CreateAccountActivity extends SingleFragmentActivity {

    @Override
    protected CreateAccountFragment createFragment() {
        return CreateAccountFragment.newInstance();
    }
}
