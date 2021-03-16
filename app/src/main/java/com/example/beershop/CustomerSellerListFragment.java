package com.example.beershop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerSellerListFragment extends Fragment {
    Button mSignoutButton;
    RecyclerView mSellerListRecyclerView;

    public static CustomerSellerListFragment newInstance() {
        CustomerSellerListFragment fragment = new CustomerSellerListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_seller_list, container, false);
        mSellerListRecyclerView = v.findViewById(R.id.seller_list_recyclerview);
        mSignoutButton = v.findViewById(R.id.buttonSignout);
        mSellerListRecyclerView.setAdapter(new SellerListAdapter());
        mSellerListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    class SellerListAdapter extends RecyclerView.Adapter<SellerListAdapter.SellerViewHolder> {
        private String[] localDataSet;
        private Context mContext;

        public SellerListAdapter() {

        }

        @NonNull
        @Override
        public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //Create a new view, which defines the UI of the list item
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sellerrecyclerview_item, parent, false);
            return new SellerViewHolder(v, mContext);

        }

        //Create new views

        @Override
        public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {
        /* Get element from your dataset at this position and replace  the contents of the view
        with that element
         */
        }

        // Return the size of the dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return 10;
        }

        public class SellerViewHolder extends RecyclerView.ViewHolder {
            private final TextView mSellerName;
            private final ImageView mLogo;
            private final Button mVisitButton;

            public SellerViewHolder(View v, Context context) {
                super(v);

                mSellerName = v.findViewById(R.id.visitButton);
                mLogo = v.findViewById(R.id.sellerLogo);
                mVisitButton = v.findViewById(R.id.visitButton);
                mContext = context;
                //On button press
                //Visit the seller shop
                mVisitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), CustomerShopPageActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}