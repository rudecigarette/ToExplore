package com.example.materialtest.activities;

import android.content.Intent;
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
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.materialtest.R;
import com.example.materialtest.utils.AppBarLayoutStateChangeListener;
import com.example.materialtest.utils.StatusBarUtils;
import com.zhuang.likeviewlibrary.LikeView;

public class FruitActivity extends AppCompatActivity {
    public static final String FRUIT_NAME = "fruit_name";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    AppBarLayout appBarLayout;
    FloatingActionButton floatingActionButton;
    LikeView likeView;
    TextView textView1;
    Switch aSwitch;
    TextView textView2;
    int likecount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarLayoutStateChangeListener.State state) {
                switch (state) {
                    case EXPANDED:
                        StatusBarUtils.setTransparent(FruitActivity.this);
                        break;
                    case COLLAPSED:
                        StatusBarUtils.setColor(FruitActivity.this,getResources().getColor(R.color.colorPrimary));
                        break;
                    case INTERMEDIATE:
                        StatusBarUtils.setTransparent(FruitActivity.this);
                        break;
                }
            }
        });
        textView1 = findViewById(R.id.likecountTextView);
        textView2 = findViewById(R.id.collect);
        aSwitch = findViewById(R.id.collectSwitch);
        likeView = findViewById(R.id.likeView);
        floatingActionButton = findViewById(R.id.shareFAB);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView1.setText(likecount+"");
                textView2.setText("收藏分享");
            }
        });
        likeView.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
                likecount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView1.setText(likecount+"");
                    }
                });
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"这是一条分享内容");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"选择分享应用"));
            }
        });



        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
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
        collapsingToolbar.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent = generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);
    }
    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
