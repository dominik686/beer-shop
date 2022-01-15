package com.example.beershop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.example.beershop.models.BasketModel;
import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.singletons.CurrentSeller;
import com.example.beershop.singletons.CurrentUser;
import com.example.beershop.utils.AnimationHelper;
import com.example.beershop.viewmodels.CustomerBasketViewModel;

public class CustomerBasketFragment extends Fragment {
    RecyclerView mBasketList;
    ImageButton mSignOutButton;
    Button mBuyButton;

    LottieAnimationView animationView;


    CustomerBasketViewModel mViewModel ;
    public static CustomerBasketFragment newInstance() {
        CustomerBasketFragment fragment = new CustomerBasketFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_basket, container, false);
        mSignOutButton = v.findViewById(R.id.buttonSignout);
        mBasketList = v.findViewById(R.id.basketList);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.rubberBand(mSignOutButton);

                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });
        mBuyButton = v.findViewById(R.id.buy_button);

        mViewModel = new CustomerBasketViewModel(getContext());
        animationView = v.findViewById(R.id.confetti);

        mBasketList.setAdapter(new BasketListAdapter(mViewModel.getBasket()));
        mBasketList.setLayoutManager(new LinearLayoutManager(getContext()));


        //If the basket isnt empty
        if (mViewModel.isBasketEmpty()) {
            return v;
        }

        //Allow the user to use the fragment
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.bounce(mBuyButton);

                BasketListAdapter adapter = (BasketListAdapter) mBasketList.getAdapter();
                BasketModel basketCopy = adapter.mBasketModel;
                //Update the database
                 mViewModel.removeFromInventory(basketCopy.getBeers());

                // Update the current user singleton with the new inventory and clear the basket
                mViewModel.clearBasket();
                mViewModel.updateInventory();

                // Play animations
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_fade);
                mBasketList.startAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        animationView.playAnimation();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBasketList.setAdapter(null);
                        animationView.cancelAnimation();

                        getActivity().finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        return v;
    }


    private class BasketListAdapter extends RecyclerView.Adapter<BasketListAdapter.BasketItemHolder> {

        BasketModel mBasketModel;

        public BasketListAdapter(BasketModel pBasketModel) {
            mBasketModel = pBasketModel;
        }

        @NonNull
        @Override
        public BasketItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.beerrecyclerview_item, parent, false);
            return new BasketItemHolder(v, getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull BasketItemHolder holder, int position) {
            holder.mBeer = mBasketModel.getBeers().get(position);
            holder.updateViews(holder.mBeer);
        }

        @Override
        public int getItemCount() {
            return mBasketModel.getBeers().size();
        }

        class BasketItemHolder extends RecyclerView.ViewHolder {
            private final TextView mBeerName;
            private final TextView mBeerCategory;
            private final TextView mBeerBrewery;
            private final TextView mBeerQuantity;
            private final TextView mBeerQuantityNumber;

            private final ImageView mBeerImage;
            private final ImageButton mRemoveButton;
            private final Context mContext;
            private BeerModel mBeer;

            public BasketItemHolder(@NonNull View itemView, Context pContext) {
                super(itemView);

                mBeerName = itemView.findViewById(R.id.tv_beer_name);
                mBeerCategory = itemView.findViewById(R.id.tv_beer_category);
                mBeerBrewery = itemView.findViewById(R.id.tv_beer_brewery);

                mBeerImage = itemView.findViewById(R.id.beerImage);
                mBeerQuantity = itemView.findViewById(R.id.tv_quantity1);
                mBeerQuantityNumber = itemView.findViewById(R.id.tv_quantity_number);


                mRemoveButton = itemView.findViewById(R.id.removeFromBasketButton);
                mRemoveButton.setBackgroundResource(android.R.color.transparent);
                mRemoveButton.setImageResource(R.drawable.minus);

                mRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateQuantity(-1);
                        AnimationHelper.bounce(itemView);

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
                        }

                    }
                });
                mContext = pContext;


            }

            public void updateViews(BeerModel beerModel) {
                BeerCategoryModel category = mViewModel.getCategory(beerModel);
                BeerBreweryModel brewery = mViewModel.getBrewery(beerModel);
                mBeerName.setText(beerModel.getBeerName());
                mBeerCategory.setText(category.getBeerCategoryName());
                mBeerBrewery.setText(brewery.getBeerBreweryName());


                mBeerQuantityNumber.setText(Integer.toString(beerModel.getQuantity()));

            }

            private void remove(int position, View itemView) {
                mBasketModel.removeAt(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mBasketModel.getBeers().size());
                itemView.setVisibility(View.GONE);
            }

            public void updateQuantity(int numb) {
                mBeer.addQuantity(numb);
                int currQuantity = Integer.parseInt(mBeerQuantityNumber.getText().toString());
                mBeerQuantityNumber.setText(Integer.toString(currQuantity + numb));
                //Find a way to update the quantity
                //probably just add a new textview for quantity instead of appending
            }

        }


    }
}
