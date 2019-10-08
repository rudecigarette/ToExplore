package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.materialtest.R;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

public class MeActivity extends BaseActivity {
    private TextView muserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }
    public void initView(){
        initNavbar(true,"个人中心");
        muserName = (TextView)findViewById(R.id.Username);
        muserName.setText("Hello"+" "+ UserHelp.getInstance().getPhone());
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    /*
     * 退出登录
     * */
    public void onLogoutClick(View v){
        UserUtils.Logout(this);
    }

    /*
    * 修改密码点击事件
    * */
    public void onChangeClick(View v){
        startActivity(new Intent(this,ChangePasswardActivity.class));
    }
}
