package com.example.materialtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapsdkplatform.comapi.util.SyncSysInfo;
import com.example.materialtest.R;
import com.example.materialtest.activities.FruitActivity;
import com.example.materialtest.activities.StoreActivity;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.models.Store;

import java.util.ArrayList;

public class SeachRecycleViewAdapter extends RecyclerView.Adapter<SeachRecycleViewAdapter.MyViewHolder>{

    private Context mContext;
    public static int index = -1;
    ArrayList<Store> stores= new ArrayList<>();

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_searchresult,parent,false);
        return new SeachRecycleViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Store store = stores.get(position);
        holder.resultTextView.setText(store.getStoreName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra("StoreName",store.getStoreName());
                for(int i = 0; i <FirstFragment.allStoreNameInfos.size();i++){
                    if(FirstFragment.allStoreNameInfos.get(i).getStoreName().equals(store.getStoreName())){
                        index = i;
                        break;
                    }
                }
                System.out.println("index为"+index);
                intent.putExtra("StoreId",index);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView resultTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultTextView = itemView.findViewById(R.id.searchResultTextView);
        }
    }
}
