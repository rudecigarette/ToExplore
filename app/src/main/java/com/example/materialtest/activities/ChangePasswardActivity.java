package com.example.materialtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.materialtest.R;

public class ChangePasswardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passward);
        initView();
    }
    public void initView(){
        initNavbar(true,"修改密码");
    }
}
