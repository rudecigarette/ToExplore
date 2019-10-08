package com.example.materialtest.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.example.materialtest.R;
import com.example.materialtest.Views.inputView;
import com.example.materialtest.db.User;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

import org.litepal.LitePal;

public class RegisterActivity extends BaseActivity{

    private inputView phone,passward,passward_confirm;
    private String mPhone,mPassward,mPassward_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        initNavbar(true,"注册");
        //获取输入框实例
        phone = findViewById(R.id.reg_phone);
        passward = findViewById(R.id.reg_passward);
        passward_confirm = findViewById(R.id.reg_passward_confirm);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
    /*
     *注册 按钮点击事件
     * 1.用户输入合法性验证
     *      1.手机号是否合法
     *      2.用户是否输入并确认了密码，且两次密码输入相同
     *      3.用户所输入的手机号是否已被注册
     * 2.保存用户输入的手机号与密码（MD5加密密码）
     * */
    public void onRegClick(View v){
        //获取输入框内容
        mPhone = phone.getInputStr();
        mPassward = passward.getInputStr();
        mPassward_confirm = passward_confirm.getInputStr();
        //将数据写入数据库
        boolean result = UserUtils.registerUser(this,mPhone,mPassward,mPassward_confirm);

//            注册完成后后退到登陆页面
        if(result){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }
}
