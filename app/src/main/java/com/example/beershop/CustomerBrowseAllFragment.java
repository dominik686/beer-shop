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

public class CustomerBrowseAllFragment extends Fragment {
    RecyclerView mBeerList;
    Button mBasket;
    Button mSignOut;
    BeerDataBaseHelper beerDBHelper;

    public static CustomerBrowseAllFragment newInstance() {

        CustomerBrowseAllFragment fragment = new CustomerBrowseAllFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_browse_all, container, false);
        mBeerList = v.findViewById(R.id.beer_recyclerview);
        mBasket = v.findViewById(R.id.basketButton);
        mSignOut = v.findViewById(R.id.buttonSignout);

        beerDBHelper = new BeerDataBaseHelper(getContext());
        beerDBHelper.debug();
        mBeerList.setAdapter(new BeerListAdapter());
        mBeerList.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }


    private class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.BeerHolder> {

        @NonNull
        @Override
        public BeerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beerrecyclerview_item, parent, false);

            return new BeerHolder(v, getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull BeerHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        private class BeerHolder extends RecyclerView.ViewHolder {
            private final TextView mBeerName;
            private final ImageView mBeerImage;
            private final Button mAddButton;
            private final Context mContext;

            public BeerHolder(@NonNull View itemView, Context pContext) {
                super(itemView);
                mBeerName = itemView.findViewById(R.id.beerName);
                mBeerImage = itemView.findViewById(R.id.beerImage);
                mAddButton = itemView.findViewById(R.id.removeFromBasketButton);
                mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Beer added to the basket!", Toast.LENGTH_LONG).show();
                    }
                });
                mContext = pContext;
            }
        }
    }

}

