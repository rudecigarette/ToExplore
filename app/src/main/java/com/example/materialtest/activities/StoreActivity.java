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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.adapter.RecommdListAdapter;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.models.Store;
import com.example.materialtest.models.StoreDetail;
import com.example.materialtest.models.StoreInfo;
import com.example.materialtest.models.StoreName;
import com.example.materialtest.utils.AppBarLayoutStateChangeListener;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.SpacesItemDecoration;
import com.example.materialtest.utils.StatusBarUtils;
import com.zhuang.likeviewlibrary.LikeView;

import java.util.ArrayList;
import java.util.Random;

public class StoreActivity extends AppCompatActivity {
    public static final int UPDATE_TEXT = 1;
    private Handler handler;
    private int likecount = 0;
    public String storePhone;
    public String openTime;
    public String storeDetail;
    private ArrayList<Store> sources = new ArrayList<>();
    public static StoreInfo storeInfo = null;
    public static ArrayList<StoreInfo> allStoreInfo = new ArrayList<>();
    private String Storename = null;
    private int StoreId = 0;
    public FloatingActionButton floatingActionButton;
    public TextView likecountTextView;
    public TextView open_time;
    public TextView store_phone;
    public TextView haveData;
    public TextView shopDetail;
    public static String LabelRec;
    AppBarLayout appBarLayout;
    LikeView likeView;
    Switch aSwitch;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private LinearLayout LL;
    private LinearLayout LL2;
    private LinearLayout LL3;
    private Button buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Intent intent = getIntent();
        Storename = intent.getStringExtra("StoreName");
        Toolbar toolbar = findViewById(R.id.toolbar);
        open_time = findViewById(R.id.open_time);
        haveData = findViewById(R.id.haveData);
        haveData.setVisibility(View.GONE);
        store_phone = findViewById(R.id.store_phone);
        shopDetail = findViewById(R.id.storeDetail);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);
        recyclerView = findViewById(R.id.RecommdListRV);
        recyclerView2 = findViewById(R.id.RecommdListRV2);
        recyclerView3 = findViewById(R.id.RecommdListRV3);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView3.setNestedScrollingEnabled(false);
        LL = findViewById(R.id.LL);
        LL2 = findViewById(R.id.LL2);
        LL3 = findViewById(R.id.LL3);
        buy = findViewById(R.id.buy);
        setVisibility();
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
        MysqlUtil.addLookShop(UserHelp.getInstance().getPhone(),StoreId+1+"");
        initStoreDetails(StoreId%10);
        final String Storeid = String.valueOf(StoreId);
        final String Storei = String.valueOf(StoreId+1);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MysqlUtil.buyOneStore(Storei,UserHelp.getInstance().getPhone().toString());
                Toast.makeText(StoreActivity.this,"添加购买成功！",Toast.LENGTH_SHORT).show();

            }
        });
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
                store_phone.setText("商家电话："+storePhone);
                open_time.setText("营业时间："+openTime);
                shopDetail.setText("商家详情："+storeDetail);
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
        initRv2();
        initRv3();
    }
    public void setVisibility(){
        int RV_LENGTH = FirstFragment.labelRecStores.size();
        int RV2_LENGTH = FirstFragment.historyRec.size();
        int RV3_LENGTH = FirstFragment.guessyoulikeRec.size();
//        若三个推荐结果都为空，则显示暂无数据
        if(RV_LENGTH==0&&RV2_LENGTH==0&&RV3_LENGTH==0){
            recyclerView.setVisibility(View.GONE);
            LL2.setVisibility(View.GONE);
            LL3.setVisibility(View.GONE);
            haveData.setVisibility(View.VISIBLE);
            return;
        }
//        结果为空的数据集不显示
        if(RV_LENGTH==0){
            recyclerView.setVisibility(View.GONE);
            haveData.setVisibility(View.VISIBLE);
        }
        if(RV2_LENGTH==0){
            LL2.setVisibility(View.GONE);
        }
        if(RV3_LENGTH==0){
            LL3.setVisibility(View.GONE);
        }
//        若有两个集合有数据，则随机显示一个集合的数据
        if(RV_LENGTH!=0&&RV2_LENGTH!=0){
            Random random = new Random();
            int i = random.nextInt(2);
            switch (i){
                case 0:
                    LL2.setVisibility(View.GONE);
                    LL.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    LL.setVisibility(View.GONE);
                    LL2.setVisibility(View.VISIBLE);
                    break;

                    default:
                        break;
            }

        }
        if(RV_LENGTH!=0&&RV3_LENGTH!=0){
            Random random = new Random();
            int i = random.nextInt(2);
            switch (i){
                case 0:
                    LL3.setVisibility(View.GONE);
                    LL.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    LL.setVisibility(View.GONE);
                    LL3.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

        }
        if(RV2_LENGTH!=0&&RV3_LENGTH!=0){
            Random random = new Random();
            int i = random.nextInt(2);
            switch (i){
                case 0:
                    LL2.setVisibility(View.GONE);
                    LL3.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    LL3.setVisibility(View.GONE);
                    LL2.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
//        若三个推荐的结果集都有数据，则随机显示一个集合的数据
        if(RV_LENGTH!=0&&RV2_LENGTH!=0&&RV3_LENGTH!=0) {
            Random random = new Random();
            int index = random.nextInt(3);
            switch (index) {
                case 0:
                    LL.setVisibility(View.VISIBLE);
                    LL2.setVisibility(View.GONE);
                    LL3.setVisibility(View.GONE);
                    break;
                case 1:
                    LL.setVisibility(View.GONE);
                    LL2.setVisibility(View.VISIBLE);
                    LL3.setVisibility(View.GONE);
                    break;
                case 2:
                    LL.setVisibility(View.GONE);
                    LL2.setVisibility(View.GONE);
                    LL3.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

    }
    public void initRv(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecommdListAdapter adapter = new RecommdListAdapter();
        adapter.setStores(FirstFragment.labelRecStores);
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView.setAdapter(adapter);
    }
    public void initRv2(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayoutManager);
        RecommdListAdapter adapter = new RecommdListAdapter();
        adapter.setStores(FirstFragment.historyRec);
        recyclerView2.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView2.setAdapter(adapter);
    }
    public void initRv3(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(linearLayoutManager);
        RecommdListAdapter adapter = new RecommdListAdapter();
        adapter.setStores(FirstFragment.guessyoulikeRec);
        recyclerView3.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView3.setAdapter(adapter);
    }
    public static void refreshLabelRecStores(){
        FirstFragment.labelRecStores.clear();
        MysqlUtil.getLabelRec(UserHelp.getInstance().getPhone());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("获取到的LabelRec字符串为"+LabelRec);
        if(LabelRec==null){
            return;
        }
        String arr[] = LabelRec.split("\\|");
        for(int i = 0;i<arr.length;i++){
            FirstFragment.labelRecStores.add(FirstFragment.stores.get(Integer.parseInt(arr[i])-1));
        }
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
    public void initStoreDetails(int StoreId){
        if(StoreId+1<=FirstFragment.storeDetails.size()){
            storePhone = FirstFragment.storeDetails.get(StoreId).getStorePhone();
            openTime = FirstFragment.storeDetails.get(StoreId).getOpenTime();
            storeDetail = FirstFragment.storeDetails.get(StoreId).getShopDetail();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MysqlUtil.getAllStoreClickandNameInfo();
        StoreActivity.refreshLabelRecStores();
        MysqlUtil.getGuessyoulikeRec(UserHelp.getInstance().getPhone());
        MysqlUtil.getHistoryRec(UserHelp.getInstance().getPhone());
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
