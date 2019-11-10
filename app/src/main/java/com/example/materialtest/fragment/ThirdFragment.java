package com.example.materialtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.materialtest.R;
import com.example.materialtest.adapter.StoreAdapter;
import com.example.materialtest.models.Store;

import java.util.ArrayList;
import java.util.Random;


public class ThirdFragment extends Fragment {
    RecyclerView recyclerView;
    StoreAdapter myAdapter;
    ArrayList<Store>stores =new ArrayList<>();
    Store []storess ={new Store("益和堂（阳光在线广场店）","南海区线阳光在线广场一层A区大堂A铺",R.drawable.s1),
                        new Store("书亦烧仙草（狮山大学城店）","南海区在线商业广场一层B区1号铺",R.drawable.s2)
                        };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        for(int i=0;i<50;i++){
            Random random = new Random();
            int j = random.nextInt(2);
            stores.add(storess[j]);
        }
        recyclerView = view.findViewById(R.id.myRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new StoreAdapter();
        myAdapter.setStores(stores);
        myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);



        return view;
    }
}

