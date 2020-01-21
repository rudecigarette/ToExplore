package com.example.materialtest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.materialtest.R;
import com.example.materialtest.activities.Test;
import com.example.materialtest.adapter.FruitAdapter;
import com.example.materialtest.models.Fruit;
import com.example.materialtest.models.Store;
import com.example.materialtest.utils.ReadtxtUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SecondFragment extends Fragment {
    @Nullable
    private List<Store> stores = new ArrayList<>();

    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
            swipeRefresh = view.findViewById(R.id.swipe_refresh);
            swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshFruits();
                }
            });

            initFruits();
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new FruitAdapter(stores);
            recyclerView.setAdapter(adapter);
        return view;
    }
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    private void initFruits() {
        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        String storeName = "";
        String storeInfo = "";
        String storePic = "";
        int resourceId ;
        List<String> data = ReadtxtUtil.getString(inputStream);
        Context ctx = getContext();



        for(int i=0;i<data.size();i++){
            storeName = data.get(i).split(",")[1];
            storeInfo = "评分："+data.get(i).split(",")[2]+"  "+data.get(i).split(",")[3];
            storePic = data.get(i).split(",")[4];
            resourceId = getResources().getIdentifier(storePic,"drawable",ctx.getPackageName());
            Store store = new Store(storeName,storeInfo,resourceId);
            stores.add(store);
        }
    }
}

