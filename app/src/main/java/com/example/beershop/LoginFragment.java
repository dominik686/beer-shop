package com.example.beershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    ImageView mImage;
    EditText mUsername;
    EditText mPassword;
    Button mCreateAccountButton;
    Button mLoginButton;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mImage = v.findViewById(R.id.logoImage);
        mUsername = v.findViewById(R.id.username);
        mPassword = v.findViewById(R.id.password);
        mCreateAccountButton = v.findViewById(R.id.loginCreateAccountButton);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        mLoginButton = v.findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerSellerListActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}

//TODO:
/* -make it so that you can press login unless the two fields are filled
   -make a minimum amount of characters for each field?
   -Add login button functionality
   -Add a picture
   -
* */