package com.example.beershop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.example.beershop.R;
import com.example.beershop.activities.CustomerBasketActivity;
import com.example.beershop.activities.CustomerBrowseAllActivity;
import com.example.beershop.activities.CustomerScanBarcodeActivity;
import com.example.beershop.activities.LoginActivity;
import com.example.beershop.activities.ResellerMainPageActivity;
import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.models.ResellerModel;
import com.example.beershop.singletons.CurrentUser;
import com.example.beershop.utils.AnimationHelper;

import java.util.ArrayList;
import java.util.List;

public class ResellerAddBeerFragment extends Fragment {
    TextView mBeerNameLabel;
    TextView mBeerImageLabel;
    TextView mBeerBreweryLabel;
    TextView mBeerCategoryLabel;
    TextView mBeerBarcodeLabel;
    TextView mBeerQuantityLabel;

    BeerDataBaseHelper mBeerDBHelper;
    UserDataBaseHelper mUserDBHelper;
    CurrentUser mCurrentUser;
    EditText mBeerBarcode;
    EditText mBeerName;
    EditText mBeerImage;
    EditText mBeerQuantity;
    Spinner mBeerBrewery;
    Spinner mBeerCategory;

    Button mAddBeer;

    public static ResellerAddBeerFragment newInstance() {

        Bundle args = new Bundle();

        ResellerAddBeerFragment fragment = new ResellerAddBeerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reseller_add_beer, container, false);

        mBeerNameLabel = v.findViewById(R.id.name_label);
        mBeerCategoryLabel = v.findViewById(R.id.category_label);
        mBeerImageLabel = v.findViewById(R.id.image_label);
        mBeerBreweryLabel = v.findViewById(R.id.category_label);
        mBeerBarcodeLabel = v.findViewById(R.id.barcode_label);
        mBeerQuantityLabel = v.findViewById(R.id.quanity_label);

        mBeerName = v.findViewById(R.id.et_beer_name);
        mBeerImage = v.findViewById(R.id.et_beer_image);
        mBeerQuantity = v.findViewById(R.id.et_beer_quantity);
        mBeerBarcode = v.findViewById(R.id.et_beer_barcode);
        mBeerBrewery = v.findViewById(R.id.sp_beer_brewery);
        mBeerCategory = v.findViewById(R.id.sp_beer_category);

        mBeerDBHelper = new BeerDataBaseHelper(getContext());
        mUserDBHelper = new UserDataBaseHelper(getContext());
        mCurrentUser = CurrentUser.getInstance();

        mAddBeer = v.findViewById(R.id.add_beer_button);

        //Get categories and breweries from the db
        List<BeerCategoryModel> categoryList = mBeerDBHelper.getAllCategories();
        List<BeerBreweryModel> brewerylist = mBeerDBHelper.getAllBreweries();

        //Initialize adapters that contain the names of categories and breweries
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getCategoryNames(categoryList));
        ArrayAdapter<String> breweryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getBreweryNames(brewerylist));

        //Set the adapters
        mBeerCategory.setAdapter(categoryAdapter);
        mBeerBrewery.setAdapter(breweryAdapter);



        //When add button is pressed
        mAddBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get all the necessary data from views
                String beerImageString = mBeerImage.getText().toString();
                String beerNameString = mBeerName.getText().toString();
                String beerBarcodeString = mBeerBarcode.getText().toString();
                BeerCategoryModel category = categoryList.get(mBeerCategory.getSelectedItemPosition());
                BeerBreweryModel brewery = brewerylist.get(mBeerBrewery.getSelectedItemPosition());
                int beerCategoryID = category.getBeerCategoryID();
                int beerBreweryID = brewery.getBeerBreweryID();
                int beerQuantity = Integer.parseInt(mBeerQuantity.getText().toString());

                //Create a Beer data model from the data
                BeerModel bm = new BeerModel(-1, beerNameString, beerImageString,
                        beerCategoryID, beerBreweryID, beerBarcodeString);

                //Are all of the boxes filled?
                if (TextUtils.isEmpty(beerImageString)) {
                    AnimationHelper.shake(mBeerImage);
                    mBeerImage.setError("Please put in the name of the image.");
                }
                if (TextUtils.isEmpty(beerNameString)) {
                    AnimationHelper.shake(mBeerName);
                    mBeerName.setError("Please put in the name of the beer.");
                }
                if (TextUtils.isEmpty(beerBarcodeString)) {
                    AnimationHelper.shake(mBeerBarcode);
                    mBeerBarcode.setError("Please put in the barcode.");
                }
                if (!TextUtils.isEmpty(beerBarcodeString) && !TextUtils.isEmpty(beerNameString)
                        && !TextUtils.isEmpty(beerImageString)) {

                    //If the beer doesnt already exist, add it to the DB
                    if (!mBeerDBHelper.checkIfBeerExists(bm)) {
                        mBeerDBHelper.addBeer(bm);

                        ResellerModel rm = mCurrentUser.getResellerModel();
                        //Delete the activity and make a toast to notife the user
                        mUserDBHelper.addBeerToInventory(rm, mBeerDBHelper.getBeerId(bm), beerQuantity);

                    }
                    //If the beer already exists in the DB and not the inventory, add it to the inventory
                    else if (mUserDBHelper.getBeer(bm, mCurrentUser.getResellerModel()) == null) {
                        mUserDBHelper.addBeerToInventory(mCurrentUser.getResellerModel(),
                                bm.getBeerID(), bm.getQuantity());

                    }
                    //If the beer already exists in the DB and inventory, add the quantity
                    else if (mUserDBHelper.getBeer(bm, mCurrentUser.getResellerModel()) != null) {
                        mUserDBHelper.updateQuantity(bm, mCurrentUser.getResellerModel());
                    }

                    Toast.makeText(getContext(), "Your beer has been added!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), ResellerMainPageActivity.class);

                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(intent, bundle);

                    //If the beer already exists in the inventory, add the quantity
                }
            }
        });
        return v;
    }

    //Get names from the list of category models
    public List<String> getCategoryNames(List<BeerCategoryModel> list) {
        ArrayList<String> returnList = new ArrayList<>();
        for (BeerCategoryModel model : list
        ) {
            returnList.add(model.getBeerCategoryName());
        }

        return returnList;
    }

    //Get names from the list of brewery models
    public List<String> getBreweryNames(List<BeerBreweryModel> list) {
        ArrayList<String> returnList = new ArrayList<>();
        for (BeerBreweryModel model : list
        ) {
            returnList.add(model.getBeerBreweryName());
        }

        return returnList;
    }

    //TODO:
    //-app crashes when trying to get inventory that doesnt exist (empty string)
    //maybe try adding a check somewhere in the methods, that when inventory equals "" dont do anything

    public static class CustomerShopPageFragment extends Fragment {
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

                    requireActivity().finish();
                }
            });

            return v;
        }
    }
}
