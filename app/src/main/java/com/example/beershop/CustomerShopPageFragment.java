package com.example.beershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.example.beershop.utils.AnimationHelper;

import java.util.Objects;

public class CustomerShopPageFragment extends Fragment {
    ImageButton mSignOutButton;
    ImageButton mBasketButton;
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
                AnimationHelper.rubberBand(v);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });
        mBasketButton = v.findViewById(R.id.basket_button);
        mBasketButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AnimationHelper.rubberBand(v);
                Intent intent = new Intent(getContext(), CustomerBasketActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });
        mCategoriesButton = v.findViewById(R.id.browseAllButton);
        mBrowseAllButton = v.findViewById(R.id.browseAllButton);
        mBrowseAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.bounce(mBrowseAllButton);

                Intent intent = new Intent(getContext(), CustomerBrowseAllActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });

        mScanBarcodeButton = v.findViewById(R.id.scanBarcodeButton);
        mScanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerScanBarcodeActivity.class);
                startActivity(intent);
            }
        });

        mGoBackButton = v.findViewById(R.id.goBackButton);
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.bounce(mGoBackButton);

                Objects.requireNonNull(getActivity()).finish();
            }
        });

        return v;
    }
}
