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

public class CustomerBasketFragment extends Fragment {
    RecyclerView mBasketList;
    Button mSignOut;

    public static CustomerBasketFragment newInstance() {
        CustomerBasketFragment fragment = new CustomerBasketFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_basket, container, false);
        mSignOut = v.findViewById(R.id.buttonSignout);
        mBasketList = v.findViewById(R.id.basketList);

        mBasketList.setAdapter(new BasketListAdapter());
        mBasketList.setLayoutManager(new LinearLayoutManager(getContext()));


        return v;
    }


    class BasketListAdapter extends RecyclerView.Adapter<BasketListAdapter.BasketItemAdapter> {
        @NonNull
        @Override
        public BasketItemAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.basketrecyclerview_item, parent, false);
            return new BasketItemAdapter(v, getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull BasketItemAdapter holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class BasketItemAdapter extends RecyclerView.ViewHolder {
            private final TextView mBeerName;
            private final ImageView mBeerImage;
            private final Button mRemoveButton;
            private final Context mContext;

            public BasketItemAdapter(@NonNull View itemView, Context pContext) {
                super(itemView);
                mBeerName = itemView.findViewById(R.id.beerName);
                mBeerImage = itemView.findViewById(R.id.beerImage);
                mRemoveButton = itemView.findViewById(R.id.removeFromBasketButton);
                mRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Beer removed from basket!", Toast.LENGTH_LONG).show();
                    }
                });
                mContext = pContext;
            }
        }
    }
}
