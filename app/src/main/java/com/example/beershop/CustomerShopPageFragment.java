package com.example.beershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CustomerShopPageFragment extends Fragment {
    Button mSignOutButton;
    Button mBasketButton;
    Button mCategoriesButton;
    Button mBrowseAllButton;
    Button mScanBarcodeButton;
    Button mGoBackButton;


    public static CustomerShopPageFragment newInstance() {

        Bundle args = new Bundle();

        CustomerShopPageFragment fragment = new CustomerShopPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_shop_page, container, false);
        mSignOutButton = v.findViewById(R.id.buttonSignout);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        mBasketButton = v.findViewById(R.id.basketButton);
        mBasketButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerBasketActivity.class);
                startActivity(intent);
            }
        });
        mCategoriesButton = v.findViewById(R.id.browseAllButton);
        mBrowseAllButton = v.findViewById(R.id.browseAllButton);
        mBrowseAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerBrowseAllActivity.class);
                startActivity(intent);
            }
        });
        mScanBarcodeButton = v.findViewById(R.id.scanBarcodeButton);

        mGoBackButton = v.findViewById(R.id.goBackButton);
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }
}
