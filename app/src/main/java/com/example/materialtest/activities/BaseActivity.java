package com.example.materialtest.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.materialtest.R;

public class BaseActivity extends Activity {
    private ImageView mynv_back;
    private TextView mytv_title;
    /*
    * 实现动态初始化nav栏
    * */
    protected void initNavbar(boolean isShowBack,String title){
        mynv_back = findViewById(R.id.nv_back);
        mytv_title = findViewById(R.id.tv_title);
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
