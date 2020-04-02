package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.materialtest.R;

public class ShareShowActivity extends AppCompatActivity {

    TextView content;
    TextView title;
    TextView user;
    TextView shopname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_show);
        content = findViewById(R.id.showcontent);
        title = findViewById(R.id.showtitle);
        user = findViewById(R.id.showuser);
        shopname = findViewById(R.id.showshopname);
        Intent intent = getIntent();
        String showContent = intent.getStringExtra("shareContent");
        String showTitle = intent.getStringExtra("shareTitle");
        String showUser = intent.getStringExtra("shareUser");
        String showShopname = intent.getStringExtra("shareShopname");
        content.setText(showContent);
        title.setText(showShopname);
        user.setText(showUser);
        shopname.setText(showTitle);

    }
}
