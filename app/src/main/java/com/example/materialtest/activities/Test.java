package com.example.materialtest.activities;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.Toast;

import com.example.materialtest.R;
import com.example.materialtest.adapter.SeachRecycleViewAdapter;
import com.example.materialtest.models.Store;
import com.example.materialtest.utils.ReadtxtUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Test extends AppCompatActivity {
    SearchView.SearchAutoComplete searchAutoComplete;
    private SeachRecycleViewAdapter adapter;
    private SearchView searchView = null;
    private RecyclerView searchRV = null;
    private ArrayList<Store> stores = new ArrayList<>();
    private ArrayList<Store> sources = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        searchView = findViewById(R.id.searchEdit);
        searchRV = findViewById(R.id.searchRecycleView);
        searchRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initData();
        stores.clear();
        adapter = new SeachRecycleViewAdapter();
        adapter.setStores(stores);
        adapter.notifyDataSetChanged();
        searchRV.setAdapter(adapter);
//        设置searchView样式
        final EditText editText = (EditText)searchView.findViewById(R.id.search_src_text);
        editText.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        editText.setHintTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("请输入要查询的商家");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query)) {
                    Toast.makeText(Test.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                    searchRV.setAdapter(adapter);
                }
                else
                {
                    stores.clear();
                    for(int i = 0; i < sources.size(); i++)
                    {
                        Store sourceStore = sources.get(i);
                        if(sourceStore.getStoreName().equals(query))
                        {
                            stores.add(sourceStore);
                            break;
                        }
                    }
                    if(stores.size() == 0)
                    {
                        Toast.makeText(Test.this, "查找的商家不在列表中", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Test.this, "查找完成", Toast.LENGTH_SHORT).show();
                        adapter.setStores(stores);
                        adapter.notifyDataSetChanged();
                        searchRV.setAdapter(adapter);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    stores.clear();
                    adapter.setStores(stores);
                    searchRV.setAdapter(adapter);
                }
                else
                {
                    stores.clear();
                    for(int i = 0; i < sources.size(); i++)
                    {
                        Store sourceStore = sources.get(i);
                        if(sourceStore.getStoreName().contains(newText))
                        {
                            stores.add(sourceStore);
                        }
                    }
                    adapter = new SeachRecycleViewAdapter();
                    adapter.setStores(stores);
                    adapter.notifyDataSetChanged();
                    searchRV.setAdapter(adapter);
                }
                return true;

            }
        });


    }
    public void initData(){
        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        String storeName = "";
        String storeInfo = "";
        String storePic = "";
        int resourceId ;
        List<String> data = ReadtxtUtil.getString(inputStream);
        Context ctx = getApplicationContext();



        for(int i=0;i<data.size();i++){
            storeName = data.get(i).split(",")[1];
            storeInfo = "评分："+data.get(i).split(",")[2]+"  "+data.get(i).split(",")[3];
            storePic = data.get(i).split(",")[4];
            resourceId = getResources().getIdentifier(storePic,"drawable",ctx.getPackageName());
            Store store = new Store(storeName,storeInfo,resourceId);
            sources.add(store);
        }
    }
}
