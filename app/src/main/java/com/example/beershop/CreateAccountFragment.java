package com.example.beershop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.CustomerModel;
import com.example.beershop.models.ResellerModel;
import com.example.beershop.utils.AnimationHelper;

public class CreateAccountFragment extends Fragment {
    ImageView mImage;
    Button mCreateAccountButton;
    EditText mUsername;
    EditText mPassword;
    Switch mCustomerSwitch;
    UserDataBaseHelper mUserDBHelper;

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
        mCustomerSwitch = v.findViewById(R.id.sw_customer);
        mUserDBHelper = new UserDataBaseHelper(getContext());
        mCreateAccountButton = v.findViewById(R.id.CreateAccountCreateAccountButton);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If username or password box is empty
                //Tell the user to fill both boxes
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("Please put in your username.");
                    AnimationHelper.shake(mUsername);

                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Please put in your password.");
                    AnimationHelper.shake(mPassword);

                } else {
                    AnimationHelper.bounce(mCreateAccountButton);

                    if (mCustomerSwitch.isChecked()) {

                        //Add the account to the customer table
                        createNewCustomerAccount(username, password);
                    } else {
                        //Add the account to the reseller database
                        createNewResellerAccount(username, password);
                    }
                }


            }
        });


        return v;
    }

    public void createNewCustomerAccount(String pUsername, String pPassword) {
        CustomerModel cm = new CustomerModel(-1, pUsername, pPassword);

        // Try adding the customer to the database
        // If the method fails it will return false
        if (mUserDBHelper.addCustomer(cm)) {
            Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Account creation failed! Try a different username.", Toast.LENGTH_LONG).show();
        }
        getActivity().finish();
    }

    //Try adding new account ot he database
    public void createNewResellerAccount(String pUsername, String pPassword) {
        ResellerModel rm = new ResellerModel(-1, pUsername, pPassword);

        // Try adding the reseller to the database
        // If the method fails it will return false
        if (mUserDBHelper.addReseller(rm)) {
            Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Account creation failed! Try a different username.", Toast.LENGTH_LONG).show();
        }
        getActivity().finish();
    }


}


