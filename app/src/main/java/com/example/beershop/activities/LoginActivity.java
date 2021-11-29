package com.example.beershop.activities;

import com.example.beershop.fragments.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected LoginFragment createFragment() {
        return LoginFragment.newInstance();
    }
}
