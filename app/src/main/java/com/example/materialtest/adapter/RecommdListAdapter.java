package com.example.materialtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.activities.StoreActivity;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.models.Store;

import java.util.ArrayList;

public class RecommdListAdapter extends RecyclerView.Adapter<RecommdListAdapter.MyViewHolder>{
    private Context mContext;
    ArrayList<Store> Recommdstores= new ArrayList<>();
    public void setStores(ArrayList<Store> Recommdstores){
        this.Recommdstores = Recommdstores;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        initData();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_recommdlistrv,parent,false);
        return new RecommdListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Store store = Recommdstores.get(position);
        holder.StoretextView.setText(store.getStoreName());
        holder.StoreimageView.setImageResource(store.getResourceId());
        Glide.with(holder.itemView.getContext()).load(store.getResourceId()).into(holder.StoreimageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra("StoreName",store.getStoreName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView StoretextView;
        ImageView StoreimageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            StoreimageView = itemView.findViewById(R.id.StoreimageView);
            StoretextView = itemView.findViewById(R.id.StoretextView);
        }
    }

    public void initData(){
        setStores(FirstFragment.stores);
    }
}
