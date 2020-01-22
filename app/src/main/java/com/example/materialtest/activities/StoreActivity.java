package com.example.materialtest.activities;

import android.content.Context;
import android.content.Intent;
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
import com.example.materialtest.utils.ReadtxtUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private ArrayList<Store> sources = new ArrayList<>();
    private String Storename = null;
    public FloatingActionButton floatingActionButton;
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
