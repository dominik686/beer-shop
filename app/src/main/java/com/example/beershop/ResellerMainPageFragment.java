package com.example.beershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ResellerMainPageFragment extends Fragment {
    Button mAddNewBeersButton;
    Button mCheckSalesButton;
    RecyclerView mInventoryRecyclerview;

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

        mAddNewBeersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResellerAddBeerActivity.class);
                startActivity(intent);
            }
        });
        mInventoryRecyclerview.setAdapter(new InventoryAdapter());


        return v;
    }


    public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryHolder> {


        @NonNull
        @Override
        public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventoryrecyclerview_item, parent, false);

            return new InventoryHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class InventoryHolder extends RecyclerView.ViewHolder {

            public InventoryHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
