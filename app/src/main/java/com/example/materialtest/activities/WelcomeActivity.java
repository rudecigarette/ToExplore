package com.example.materialtest.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.materialtest.R;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.models.StoreInfo;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.NetworkCheck;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private Timer mtimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimaryori));
        //                先判断网络状态，若无网络则退出应用
        if(!NetworkCheck.isNetworkConnected(WelcomeActivity.this)){
            new AlertDialog.Builder(WelcomeActivity.this)
                    .setTitle("当前无可用网络")
                    .setMessage("请检查您的网络连接后重试")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                                    退出应用
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);

                        }
                    }).show();
        }
        else{
            init();
        }
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
                if(isLogin){
                    toMain();
                    MysqlUtil.getUserCollection(UserHelp.getInstance().getPhone());
                }else {
                    toLogin();
                }

            }
        },1*1000);
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
