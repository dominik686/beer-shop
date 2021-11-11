package com.example.beershop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.ResellerModel;
import com.example.beershop.singletons.CurrentSeller;
import com.example.beershop.utils.AnimationHelper;

import java.util.List;

public class CustomerSellerListFragment extends Fragment {
    ImageButton mSignoutButton;
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
        mSignoutButton.setOnClickListener(new View.OnClickListener() {
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
            holder.mReseller = mResellers.get(position);
            holder.mSellerName.setText(holder.mReseller.getUsername());
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
                        AnimationHelper.bounce(mVisitButton);

                        CurrentSeller.getInstance(mReseller);
                        Intent intent = new Intent(getContext(), CustomerShopPageActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(intent, bundle);
                    }
                });
            }
        }
    }
}
// Have a get all method that return an array of ResellerModels for the adapter?