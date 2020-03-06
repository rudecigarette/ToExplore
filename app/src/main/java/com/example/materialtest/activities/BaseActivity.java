package com.example.materialtest.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.materialtest.R;

public class BaseActivity extends AppCompatActivity {
    private ImageView mynv_back;
    private TextView mytv_title;
    /*
    * 实现动态初始化nav栏
    * */
    protected void initNavbar(boolean isShowBack,String title){
        mynv_back = (ImageView) findViewById(R.id.nv_back);
        mytv_title = (TextView) findViewById(R.id.tv_title);
        mynv_back.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mytv_title.setText(title);
        mynv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
