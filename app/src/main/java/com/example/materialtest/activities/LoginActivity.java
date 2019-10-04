package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialtest.R;
import com.example.materialtest.Views.inputView;
import com.example.materialtest.db.User;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.utils.UserUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private inputView mInputPhone,mInputPassward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }
    /*
    * 初始化View
    * */
    private void initView(){
        initNavbar(false,"登录");
        mInputPhone = findViewById(R.id.input_phone);
        mInputPassward = findViewById(R.id.input_passward);
    }
    /*
     *跳转注册页面点击事件
     * */
    public void onRegisterClick(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    /*
     *登录按钮点击事件
     * */
    public void onCommitClick(View v){
        String phone = mInputPhone.getInputStr();
        String passward = mInputPassward.getInputStr();
        //验证用户输入是否合法
        if(!UserUtils.validateLogin(this,phone,passward)){
            return;
        }
        Toast.makeText(this,"登录成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
