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

import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.ResellerModel;

import java.util.List;

public class CustomerSellerListFragment extends Fragment {
    Button mSignoutButton;
    RecyclerView mSellerListRecyclerView;
    UserDataBaseHelper mDBHelper;
    List<ResellerModel> mResellers;

    public static CustomerSellerListFragment newInstance() {
        CustomerSellerListFragment fragment = new CustomerSellerListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_seller_list, container, false);
        mDBHelper = new UserDataBaseHelper(getContext());
        mSellerListRecyclerView = v.findViewById(R.id.seller_list_recyclerview);
        mResellers = mDBHelper.getAllResellers();
        mSignoutButton = v.findViewById(R.id.buttonSignout);
        mSellerListRecyclerView.setAdapter(new SellerListAdapter(mResellers));
        mSellerListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDBHelper = new UserDataBaseHelper(getContext());


        return v;
    }


    class SellerListAdapter extends RecyclerView.Adapter<SellerListAdapter.SellerViewHolder> {
        private Context mContext;
        List<ResellerModel> mResellers;

        public SellerListAdapter(List<ResellerModel> pResellers) {
            mResellers = pResellers;

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
            holder.mSellerName.setText(mResellers.get(position).getUsername());
        }

        // Return the size of the dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mResellers.size();
        }

        public class SellerViewHolder extends RecyclerView.ViewHolder {
            private final TextView mSellerName;
            private final ImageView mLogo;
            private final Button mVisitButton;
            private ResellerModel mReseller;
            public SellerViewHolder(View v, Context context) {
                super(v);

                mSellerName = v.findViewById(R.id.sellerName);
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
// Have a get all method that return an array of ResellerModels for the adapter?