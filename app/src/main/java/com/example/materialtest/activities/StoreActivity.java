package com.example.materialtest.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.models.Store;
import com.example.materialtest.utils.AppBarLayoutStateChangeListener;
import com.example.materialtest.utils.ReadtxtUtil;
import com.example.materialtest.utils.StatusBarUtils;
import com.zhuang.likeviewlibrary.LikeView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    public static final int UPDATE_TEXT = 1;
    private Handler handler;
    private int likecount = 0;
    private ArrayList<Store> sources = new ArrayList<>();
    private String Storename = null;
    public FloatingActionButton floatingActionButton;
    public TextView likecountTextView;
    AppBarLayout appBarLayout;
    LikeView likeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Intent intent = getIntent();
        Storename = intent.getStringExtra("StoreName");
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);
        TextView fruitContentText = findViewById(R.id.fruit_content_text);
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
        likecountTextView = findViewById(R.id.likecountTextView);
//        使用handler来处理ui更新
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case UPDATE_TEXT:
                        likecountTextView.setText(likecount+"");
                        break;
                    default:
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message);
            }
        }).start();
        likeView = findViewById(R.id.likeView);
        likeView.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
                likecount++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
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
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
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
