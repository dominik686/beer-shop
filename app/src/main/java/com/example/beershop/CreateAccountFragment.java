package com.example.beershop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateAccountFragment extends Fragment {
    ImageView mImage;
    Button mCreateAccountButton;
    EditText mUsername;
    EditText mPassword;

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_account, container, false);

        mImage = v.findViewById(R.id.logoImage);
        mUsername = v.findViewById(R.id.username);
        mPassword = v.findViewById(R.id.password);
        mCreateAccountButton = v.findViewById(R.id.CreateAccountCreateAccountButton);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });


        return v;
    }
}

/*
TODO:
- add functionality

 */
