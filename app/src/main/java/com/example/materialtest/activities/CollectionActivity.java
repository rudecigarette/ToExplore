package com.example.materialtest.activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.materialtest.R;
import com.example.materialtest.utils.StatusBarUtils;

public class CollectionActivity extends BaseActivity {
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
    }

    private void initView() {
        initNavbar(true,"我的收藏");
        tabLayout = findViewById(R.id.tab_layout);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
        tabLayout.addTab(tabLayout.newTab().setText("商家"));
        tabLayout.addTab(tabLayout.newTab().setText("发现"));
        tabLayout.addTab(tabLayout.newTab().setText("其它"));
    }
}
