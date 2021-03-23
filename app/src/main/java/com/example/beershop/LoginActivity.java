package com.example.beershop;

public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected LoginFragment createFragment() {
        return LoginFragment.newInstance();
    }
}
