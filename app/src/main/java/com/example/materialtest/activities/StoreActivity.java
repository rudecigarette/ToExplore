package com.example.materialtest.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.adapter.RecommdListAdapter;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.models.Store;
import com.example.materialtest.models.StoreInfo;
import com.example.materialtest.models.StoreName;
import com.example.materialtest.utils.AppBarLayoutStateChangeListener;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.SpacesItemDecoration;
import com.example.materialtest.utils.StatusBarUtils;
import com.zhuang.likeviewlibrary.LikeView;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    public static final int UPDATE_TEXT = 1;
    private Handler handler;
    private int likecount = 0;
    private ArrayList<Store> sources = new ArrayList<>();
    public static StoreInfo storeInfo = null;
    public static ArrayList<StoreInfo> allStoreInfo = new ArrayList<>();
    private String Storename = null;
    private int StoreId = 0;
    public FloatingActionButton floatingActionButton;
    public TextView likecountTextView;
    AppBarLayout appBarLayout;
    LikeView likeView;
    Switch aSwitch;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Intent intent = getIntent();
        StoreId = intent.getIntExtra("StoreId",0);
        Storename = intent.getStringExtra("StoreName");
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);
        TextView fruitContentText = findViewById(R.id.fruit_content_text);
        recyclerView = findViewById(R.id.RecommdListRV);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(Storename);
        appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarLayoutStateChangeListener.State state) {
                switch (state) {
                    case EXPANDED:
                        StatusBarUtils.setTransparent(StoreActivity.this);
                        break;
                    case COLLAPSED:
                        StatusBarUtils.setColor(StoreActivity.this,getResources().getColor(R.color.colorPrimary));
                        break;
                    case INTERMEDIATE:
                        StatusBarUtils.setTransparent(StoreActivity.this);
                        break;
                }
            }
        });
        int fruitImageId = getFruitImageId(Storename);
        if(fruitImageId!=0){
            Glide.with(this).load(fruitImageId).into(fruitImageView);
        }
        String fruitContent = generateFruitContent(Storename);
        fruitContentText.setText(fruitContent);
        floatingActionButton = findViewById(R.id.shareFAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,Storename);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"选择分享应用"));
            }
        });
        aSwitch = findViewById(R.id.collectSwitch);
        ArrayList<StoreName> allStoreNames= FirstFragment.allStoreNameInfos;
        final ArrayList<String> storenames = new ArrayList<>();
        for(int i = 0;i<allStoreNames.size();i++){
            storenames.add(allStoreNames.get(i).getStoreName());
        }
        int StoreId = storenames.indexOf(Storename);
        final String Storeid = String.valueOf(StoreId);
        final String Storei = String.valueOf(StoreId+1);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aSwitch.isChecked()){
                    MysqlUtil.collectOneStore(Storei,UserHelp.getInstance().getPhone().toString());
                }else{
                    MysqlUtil.uncollectOneStore(Storei,UserHelp.getInstance().getPhone().toString());
                }
            }
        });
        likecountTextView = findViewById(R.id.likecountTextView);
        likecount = getClick(StoreId);
//        使用handler来处理ui更新
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                likecountTextView.setText(likecount+"");
                aSwitch.setChecked(ifCollect(Storename));
            }
        });
        likeView = findViewById(R.id.likeView);
        likeView.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
//              如果是第一次点击
                if(!isCancel){
                    likecount++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            likecountTextView.setText(likecount+"");
                        }
                    });
                    MysqlUtil.clickPlus(Storename);
                }
                else{
                    //不是第一次点击就啥都不干
                }

            }
        });

        initRv();
    }
    public void initRv(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecommdListAdapter adapter = new RecommdListAdapter();
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView.setAdapter(adapter);
    }
    public boolean ifCollect(String StoreName){
        String Collection = MysqlUtil.Collection;
        if(Collection==null) return false;
        String[] collectedStores = Collection.split(",|\\|");
        for(int i = 0;i<collectedStores.length;i++){
            if(collectedStores[i].equals("")||collectedStores[i].equals("null")){
                continue;
            }else{
                if(sources.get(Integer.parseInt(collectedStores[i])-1).getStoreName().equals(StoreName))
                return true;
            }
        }
        return false;
    }
    public int getClick(int StoreId){
        if(StoreId < FirstFragment.allStoreClickInfos.size()){
           return FirstFragment.allStoreClickInfos.get(StoreId).getClick();
        }else {
            return 0;
        }

    }
    public int getFruitImageId(String Storename){
        sources = FirstFragment.getStores();
        if(sources.size()!=0){
            for(int i = 0;i < sources.size();i++){
                if(sources.get(i).getStoreName().equals(Storename)){
                    return sources.get(i).getResourceId();
                }
            }
        }
        return 0;
    }
    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MysqlUtil.getAllStoreClickandNameInfo();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
