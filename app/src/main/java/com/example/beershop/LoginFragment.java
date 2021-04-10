package com.example.beershop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.CustomerModel;
import com.example.beershop.models.ResellerModel;
import com.example.beershop.singletons.CurrentUser;

public class LoginFragment extends Fragment {

    private ImageView mImage;
    private EditText mUsername;
    private EditText mPassword;
    private LottieAnimationView animationView;

    private Button mCreateAccountButton;
    private Button mLoginButton;
    private Switch mCustomerSwitch;
    private UserDataBaseHelper mUserDBHelper;
    private BeerDataBaseHelper mBeerDBHelper;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        animationView = v.findViewById(R.id.animation_view);
        animationView.playAnimation();

        boolean check = animationView.isAnimating();
        mCustomerSwitch = v.findViewById(R.id.sw_customer);
        mImage = v.findViewById(R.id.logoImage);
        mUsername = v.findViewById(R.id.username);
        mPassword = v.findViewById(R.id.password);


        mUserDBHelper = new UserDataBaseHelper(getContext());
        mBeerDBHelper = new BeerDataBaseHelper(getContext());
        mBeerDBHelper.addDefaultValues();

        mCreateAccountButton = v.findViewById(R.id.loginCreateAccountButton);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });

        mLoginButton = v.findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If username or password box is empty
                //Tell the user to fill both boxes
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("Please put in your username.");
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Please put in your password.");
                } else {


                    //If the customer switch is on search the customer table
                    //If it isnt, searchn the reseller database
                    if (mCustomerSwitch.isChecked()) {
                        //Search the customer table and if the username matches, log in
                        CustomerModel cm = new CustomerModel(-1, username, password);
                        if (mUserDBHelper.customerCredentialsCheck(cm)) {
                            CurrentUser.getInstance(getContext(), cm);

                            Intent intent = new Intent(getActivity(), CustomerSellerListActivity.class);
                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                            startActivity(intent, bundle);
                        }
                    } else {
                        //Search the reseller table, and if the username matches, log in
                        ResellerModel rm = new ResellerModel(-1, username, password);
                        if (mUserDBHelper.resellerCredentialsCheck(rm)) {
                            CurrentUser.getInstance(getContext(), rm);

                            Intent intent = new Intent(getActivity(), ResellerMainPageActivity.class);
                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                            startActivity(intent, bundle);
                        }
                    }
                }
            }
        });

        return v;
    }

}

//TODO:
/* -make it so that you can press login unless the two fields are filled
   -make a minimum amount of characters for each field?
   -Add a picture
   -
* */