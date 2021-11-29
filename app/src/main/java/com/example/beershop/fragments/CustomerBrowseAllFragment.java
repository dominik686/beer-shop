package com.example.beershop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.beershop.R;
import com.example.beershop.activities.LoginActivity;
import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.singletons.CurrentSeller;
import com.example.beershop.singletons.CurrentUser;
import com.example.beershop.utils.AnimationHelper;

import java.util.List;

public class CustomerBrowseAllFragment extends Fragment {
    RecyclerView mBeerListRecyclerview;
    ImageButton mBasket;
    ImageButton mSignOut;

    BeerDataBaseHelper mBeerDBHelper;
    UserDataBaseHelper mUserDBHelper;

    CurrentUser mCurrentUser;
    CurrentSeller mCurrentSeller;
    List<BeerModel> mBeers;

    public static CustomerBrowseAllFragment newInstance() {

        CustomerBrowseAllFragment fragment = new CustomerBrowseAllFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_browse_all, container, false);
        mBeerListRecyclerview = v.findViewById(R.id.beer_recyclerview);
        mBasket = v.findViewById(R.id.basket_button);
        mSignOut = v.findViewById(R.id.buttonSignout);

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });
        mBeerDBHelper = new BeerDataBaseHelper(getContext());
        mUserDBHelper = new UserDataBaseHelper(getContext());

        mCurrentSeller = CurrentSeller.getInstance();
        mCurrentUser = CurrentUser.getInstance();

        // Get the inventory from the DB and turn it into a list of Beer models for the adapter
        String inventory = mUserDBHelper.getInventory(mCurrentSeller.getResellerModel());


        mBeers = mBeerDBHelper.getBeerListFromInventory(inventory);
        mBeerListRecyclerview.setAdapter(new BeerListAdapter(mBeers));
        mBeerListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }


    private class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.BeerHolder> {

        List<BeerModel> mBeerList;

        private BeerListAdapter(List<BeerModel> bmList) {
            mBeerList = bmList;
        }

        @NonNull
        @Override
        public BeerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beerrecyclerview_item, parent, false);

            return new BeerHolder(v, getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull BeerHolder holder, int position) {
            holder.mBeer = mBeerList.get(position);
            holder.updateViews(holder.mBeer);

        }

        @Override
        public int getItemCount() {
            return mBeerList.size();
        }

        private class BeerHolder extends RecyclerView.ViewHolder {
            private final TextView mBeerName;
            private final TextView mBeerCategory;
            private final TextView mBeerBrewery;
            private final TextView mBeerQuantity;
            private final TextView mBeerQuantityNumber;
            private final ImageView mBeerImage;
            private final ImageButton mAddButton;
            private final Context mContext;
            private final LottieAnimationView mOKAnimation;
            private BeerModel mBeer;

            public BeerHolder(@NonNull View itemView, Context pContext) {
                super(itemView);

                mBeerName = itemView.findViewById(R.id.tv_beer_name);
                mBeerCategory = itemView.findViewById(R.id.tv_beer_category);
                mBeerBrewery = itemView.findViewById(R.id.tv_beer_brewery);
                mBeerQuantity = itemView.findViewById(R.id.tv_quantity1);
                mBeerQuantityNumber = itemView.findViewById(R.id.tv_quantity_number);
                mBeerImage = itemView.findViewById(R.id.beerImage);

                mOKAnimation = itemView.findViewById(R.id.ok_anim);


                mAddButton = itemView.findViewById(R.id.removeFromBasketButton);
                mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AnimationHelper.bounce(mAddButton);

                        if (mBeer.getQuantity() == 0) {
                            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_fade);
                            anim.setDuration(500);
                            itemView.startAnimation(anim);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    remove(getAdapterPosition(), itemView);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        } else {
                            mOKAnimation.playAnimation();
                            AnimationHelper.bounce(itemView);


                            BeerModel tempBeer = mBeer.copy();
                            mBeer.getQuantity();
                            // Change the quantity of beer to 1, and add it to the basket singleton
                            tempBeer.setQuantity(1);
                            mCurrentSeller.addToBasket(tempBeer);
                            mBeer.getQuantity();

                            // Subtract 1 from the list beers quantity and then update the quantity
                            updateQuantity(-1);
                        }

                    }
                });
                mContext = pContext;
            }

            public void updateViews(BeerModel beerModel) {
                BeerCategoryModel category = mBeerDBHelper.getCategory(beerModel.getBeerCategoryID());
                BeerBreweryModel brewery = mBeerDBHelper.getBrewery(beerModel.getBeerBreweryID());
                mBeerName.setText(beerModel.getBeerName());
                mBeerCategory.setText(category.getBeerCategoryName());
                mBeerBrewery.setText(brewery.getBeerBreweryName());
                mBeerQuantityNumber.setText(Integer.toString(beerModel.getQuantity()));
            }

            public void updateQuantity(int numb) {
                mBeer.addQuantity(numb);
                int currQuantity = Integer.parseInt(mBeerQuantityNumber.getText().toString());
                mBeerQuantityNumber.setText(Integer.toString(currQuantity + numb));

                //Find a way to update the quantity
                //probably just add a new textview for quantity instead of appending
            }

            private void remove(int position, View itemView) {
                mBeers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mBeers.size());
                itemView.setVisibility(View.GONE);
            }


        }
    }

}

// Add a BasketModel object to CurrentSeller. Basket will be individual for each reseller
//Basket class would be a list of BeerModels?
//