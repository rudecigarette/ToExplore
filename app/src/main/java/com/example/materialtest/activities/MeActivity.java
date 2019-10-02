package com.example.materialtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.materialtest.R;
import com.example.materialtest.utils.UserUtils;

public class MeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }
    public void initView(){
        initNavbar(true,"个人中心");
    }

    /*
     * 退出登录
     * */
    public void onLogoutClick(View v){
        UserUtils.Logout(this);
    }

    /*
    * 修改密码
    * */
    public void onChangeClick(View v){

    }
}
