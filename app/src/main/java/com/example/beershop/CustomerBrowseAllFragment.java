package com.example.beershop;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.BeerBreweryModel;
import com.example.beershop.models.BeerCategoryModel;
import com.example.beershop.models.BeerModel;
import com.example.beershop.singletons.CurrentSeller;
import com.example.beershop.singletons.CurrentUser;

import java.util.List;

public class CustomerBrowseAllFragment extends Fragment {
    RecyclerView mBeerListRecyclerview;
    Button mBasket;
    Button mSignOut;

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
        mBasket = v.findViewById(R.id.basketButton);
        mSignOut = v.findViewById(R.id.buttonSignout);

        mBeerDBHelper = new BeerDataBaseHelper(getContext());
        mUserDBHelper = new UserDataBaseHelper(getContext());

        mCurrentSeller = CurrentSeller.getInstance(getContext());
        mCurrentUser = CurrentUser.getInstance(getContext());

        mBeers = mBeerDBHelper.getBeerListFromInventory(mCurrentSeller.getResellerModel().getInventory());
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
            private final Button mAddButton;
            private final Context mContext;
            private BeerModel mBeer;

            public BeerHolder(@NonNull View itemView, Context pContext) {
                super(itemView);

                mBeerName = itemView.findViewById(R.id.tv_beer_name);
                mBeerCategory = itemView.findViewById(R.id.tv_beer_category);
                mBeerBrewery = itemView.findViewById(R.id.tv_beer_brewery);
                mBeerQuantity = itemView.findViewById(R.id.tv_quantity1);
                mBeerQuantityNumber = itemView.findViewById(R.id.tv_quantity_number);
                mBeerImage = itemView.findViewById(R.id.beerImage);

                mAddButton = itemView.findViewById(R.id.removeFromBasketButton);
                mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mBeer.getQuantity() == 0) {
                            remove(getAdapterPosition(), itemView);
                        } else {
                            Toast.makeText(getActivity(), "Beer added to the basket!", Toast.LENGTH_LONG).show();
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