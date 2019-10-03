package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.materialtest.R;
import com.example.materialtest.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private Timer mtimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }
    /*
    初始化--延迟执行任务
     */
    private void init(){
        final boolean isLogin = UserUtils.validateUserLogin(this);
        mtimer = new Timer();
        mtimer.schedule(new TimerTask(){
            @Override
            public void run() {
                //toMain();
                if(isLogin){
                    toMain();
                }else {
                    toLogin();
                }

            }
        },2*1000);
    }
    /*
       跳转到MainActivity
     */
    private void toMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    /*
        跳转到LoginActivity
    */
    private void toLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
