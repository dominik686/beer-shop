package com.example.beershop.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beershop.R;
import com.example.beershop.activities.ResellerAddBeerActivity;
import com.example.beershop.activities.ResellerScanBarcodeActivity;
import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.models.ResellerModel;
import com.example.beershop.singletons.CurrentUser;
import com.example.beershop.utils.AnimationHelper;

import java.util.List;

public class ResellerMainPageFragment extends Fragment {
    Button mAddNewBeersButton;
    Button mCheckSalesButton;

    RecyclerView mInventoryRecyclerview;

    CurrentUser mCurrentUser;
    BeerDataBaseHelper mBeerDBHelper;
    UserDataBaseHelper mUserDBHelper;

    public static ResellerMainPageFragment newInstance() {

        Bundle args = new Bundle();

        ResellerMainPageFragment fragment = new ResellerMainPageFragment();
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
        View v = inflater.inflate(R.layout.fragment_reseller_main_page, container, false);
        mAddNewBeersButton = v.findViewById(R.id.button_add_beer);
        mCheckSalesButton = v.findViewById(R.id.button_check_sales);
        mInventoryRecyclerview = v.findViewById(R.id.rv_inventory);


        mUserDBHelper = new UserDataBaseHelper(getContext());
        mCurrentUser = CurrentUser.getInstance();
        mBeerDBHelper = new BeerDataBaseHelper(getContext());

        mAddNewBeersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create a dialog to let the seller choose how to add beers?
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setPositiveButton("Add beer manually", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), ResellerAddBeerActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(intent, bundle);
                    }
                });
                builder.setNegativeButton("Scan barcode", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), ResellerScanBarcodeActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(intent, bundle);
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();


                dialog.show();
                /*
                AnimationHelper.bounce(mAddNewBeersButton);
                getActivity().finish();
                Intent intent = new Intent(getActivity(), ResellerAddBeerActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);

                 */
            }
        });

        mCheckSalesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.bounce(mCheckSalesButton);

            }
        });

        ResellerModel rm = mCurrentUser.getResellerModel();
        //Only get the inventory if its not empty
        String inventory = mUserDBHelper.getInventory(rm);
        if (TextUtils.isEmpty(inventory)) {

        } else {
            List<BeerModel> beerModelList = mBeerDBHelper.getBeerListFromInventory(inventory);
            mInventoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            mInventoryRecyclerview.setAdapter(new InventoryAdapter(beerModelList));
        }

        return v;
    }


    public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryHolder> {

        List<BeerModel> mBeerList;

        public InventoryAdapter(List<BeerModel> beerModelList) {
            mBeerList = beerModelList;
        }

        @NonNull
        @Override
        public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventoryrecyclerview_item, parent, false);

            return new InventoryHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {
            holder.bindBeer(mBeerList.get(position));
        }

        @Override
        public int getItemCount() {
            return mBeerList.size();
        }

        public class InventoryHolder extends RecyclerView.ViewHolder {
            TextView mBeerNameLabel;
            TextView mBeerCategoryLabel;
            TextView mBeerBreweryLabel;
            TextView mBeerQuantityLabel;
            TextView mBeerBarcodeLabel;
            ImageView mBeerThumnbnail;
            BeerModel mBeerModel;
            BeerCategoryModel mBeerCategoryModel;
            BeerBreweryModel mBeerBreweryModel;

            TextView mBeerBarcode;
            TextView mBeerName;
            TextView mBeerCategory;
            TextView mBeerBrewery;
            TextView mBeerQuantity;

            public InventoryHolder(@NonNull View v) {
                super(v);
                mBeerNameLabel = v.findViewById(R.id.tv_beer_name_label);
                mBeerCategoryLabel = v.findViewById(R.id.tv_beer_category_label);
                mBeerBreweryLabel = v.findViewById(R.id.tv_brewery_label);
                mBeerQuantityLabel = v.findViewById(R.id.tv_stock_number_label);
                mBeerBarcodeLabel = v.findViewById(R.id.tv_barcode_label);

                mBeerThumnbnail = v.findViewById(R.id.iv_beer_picture);

                mBeerBarcode = v.findViewById(R.id.tv_barcode);
                mBeerName = v.findViewById(R.id.tv_name);
                mBeerCategory = v.findViewById(R.id.tv_category);
                mBeerBrewery = v.findViewById(R.id.tv_brewery);
                mBeerQuantity = v.findViewById(R.id.tv_quantity);

            }

            public void bindBeer(BeerModel bm) {
                mBeerModel = bm;

                mBeerCategoryModel = mBeerDBHelper.getCategory(bm.getBeerCategoryID());
                mBeerBreweryModel = mBeerDBHelper.getBrewery(bm.getBeerBreweryID());

                mBeerName.setText(bm.getBeerName());
                mBeerCategory.setText(mBeerCategoryModel.getBeerCategoryName());
                mBeerBrewery.setText(mBeerBreweryModel.getBeerBreweryName());
                mBeerQuantity.setText(String.valueOf(bm.getQuantity()));
                mBeerBarcode.setText(bm.getBarcode());
            }
        }
    }
}
