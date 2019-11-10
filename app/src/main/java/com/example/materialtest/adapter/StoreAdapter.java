package com.example.materialtest.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.activities.WordActivity;
import com.example.materialtest.models.Store;

import java.io.File;
import java.util.ArrayList;

public class StoreAdapter extends  RecyclerView.Adapter<StoreAdapter.MyViewHolder>{
    ArrayList<Store> stores= new ArrayList<>();

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.textViewChinese.setText(store.getStoreInfo().toString());
        holder.textViewNumber.setText(position+1+"");
        holder.textViewEnglish.setText(store.getStoreName().toString());
        int resourceId = store.getResourceId();
        Glide.with(holder.itemView.getContext()).load(resourceId).into(holder.imageViewPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), WordActivity.class);
                intent.putExtra("WordName",holder.textViewEnglish.getText().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNumber,textViewEnglish,textViewChinese;
        ImageView imageViewPic ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            imageViewPic = itemView.findViewById(R.id.imageViewPic);
        }
    }
}
