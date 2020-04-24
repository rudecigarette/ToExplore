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
import com.example.materialtest.adapter.ThirdFragmentRVadapter;
import com.example.materialtest.models.Store;
import com.example.materialtest.utils.ReadtxtUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ThirdFragment extends Fragment {
    private RecyclerView recyclerView ;
    ArrayList<Store> thirdstores = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        recyclerView = view.findViewById(R.id.third_fragment_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ThirdFragmentRVadapter thirdFragmentRVadapter = new ThirdFragmentRVadapter();
        System.out.println("thirdStore长度为"+thirdstores.size());
        thirdFragmentRVadapter.setStores(thirdstores);
        thirdFragmentRVadapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(thirdFragmentRVadapter);
        return view;
    }
    public void initData(){
        ArrayList<Store> stores = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        String storeName = "";
        String storeInfo = "";
        String storePic = "";
        String storeScore="";
        int resourceId;
        List<String> data = ReadtxtUtil.getString(inputStream);
        Context ctx = getContext();


        for (int i = 0; i < data.size(); i++) {
            storeName = data.get(i).split(",")[1];
            storeInfo = data.get(i).split(",")[3];
            storeScore = data.get(i).split(",")[2];
            storePic = data.get(i).split(",")[4];
            resourceId = getResources().getIdentifier(storePic, "drawable", ctx.getPackageName());
            Store store = new Store(storeName, storeInfo,resourceId,storeScore);
            stores.add(store);
        }
        thirdstores.clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String userLabel = MainActivity.UserLabel;
        System.out.println("ThirdFragment获取到的userlabel为"+userLabel);
        for(int i=0;i<stores.size();i++){
            if(userLabel.indexOf(stores.get(i).getStoreInfo())!=-1){
                thirdstores.add(new Store(stores.get(i).getStoreName(),stores.get(i).getStoreInfo(),stores.get(i).getResourceId(),stores.get(i).getStoreScore()));
            }
        }
    }
}

