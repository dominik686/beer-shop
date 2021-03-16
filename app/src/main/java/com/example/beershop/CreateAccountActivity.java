package com.example.beershop;

import androidx.fragment.app.Fragment;

public class CreateAccountActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CreateAccountFragment.newInstance();
    }
}
