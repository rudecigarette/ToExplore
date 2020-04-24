package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.materialtest.R;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

public class MeActivity extends BaseActivity {
    private TextView useraccount;
    private TextView username;
    private TextView usersex;
    private TextView usersignature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }
    public void initView(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        Button button = findViewById(R.id.logoutbtn);
        button.startAnimation(animation);
        initNavbar(true,"个人中心");

        useraccount = findViewById(R.id.Useraccount);
        username = (TextView)findViewById(R.id.Username);
        usersex = findViewById(R.id.Usersex);
        usersignature = findViewById(R.id.Usersignature);

        useraccount.append(UserHelp.getInstance().getPhone());
        username.append("18软工");
        usersex.append("男");
        usersignature.append("华南师范大学软件学院");


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

    public void onEditClick(View view) {
    }
}
