package com.example.materialtest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.materialtest.R;
import com.example.materialtest.activities.MainActivity;
import com.example.materialtest.adapter.RecommdListAdapter;
import com.example.materialtest.adapter.ThirdFragmentRVadapter;
import com.example.materialtest.models.Store;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.ReadtxtUtil;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CollectStoreFragment extends Fragment {
    ArrayList<Store> collectedstores = new ArrayList<>();
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        recyclerView = view.findViewById(R.id.collection_fragment_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ThirdFragmentRVadapter thirdFragmentRVadapter = new ThirdFragmentRVadapter();
        System.out.println("changde"+collectedstores.size());
        thirdFragmentRVadapter.setStores(collectedstores);
        thirdFragmentRVadapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(thirdFragmentRVadapter);
        return view;
    }
    public void initData(){
        ArrayList<Store> stores = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        String storeName = "";
        String storeInfo = "";
        String storePic = "";
        int resourceId;
        List<String> data = ReadtxtUtil.getString(inputStream);
        Context ctx = getContext();


        for (int i = 0; i < data.size(); i++) {
            storeName = data.get(i).split(",")[1];
            storeInfo = data.get(i).split(",")[3];
            storePic = data.get(i).split(",")[4];
            resourceId = getResources().getIdentifier(storePic, "drawable", ctx.getPackageName());
            Store store = new Store(storeName, storeInfo, resourceId);
            stores.add(store);
        }
        collectedstores.clear();
        String userCollection = MysqlUtil.Collection;
        System.out.println("ThirdFragment获取到的collection为"+userCollection);
        if(userCollection.equals("")||userCollection==null) return;
        String[] collected = userCollection.split(",|\\|");
        ArrayList<String> collected2 = new ArrayList<>();
        int j=0;
        for(int i=0;i<collected.length;i++){
            if(!collected[i].equals("")){
                collected2.add(collected[i]);
            }
        }
        for(int i=0;i<collected2.size();i++){
            System.out.println(collected2.get(i));
        }
        for(int i=0;i<collected2.size();i++){
                collectedstores.add(FirstFragment.stores.get(Integer.parseInt(collected2.get(i))-1));

        }
    }
}
