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
import com.example.materialtest.utils.UserUtils;

import org.litepal.LitePal;

public class RegisterActivity extends BaseActivity{

    private inputView phone,passward,passward_confirm;
    private String mPhone,mPassward,mPassward2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        initNavbar(true,"注册");
    }
    /*
     *登录按钮点击事件
     * */
    public void onRegClick(View v){
        //获取输入框实例
        phone = findViewById(R.id.reg_phone);
        passward = findViewById(R.id.reg_passward);
        passward_confirm = findViewById(R.id.reg_passward2);
        //获取输入框内容
        mPhone = phone.getInputStr();
        mPassward = passward.getInputStr();
        mPassward2 = passward_confirm.getInputStr();
        //判断输入数据格式
        if(!RegexUtils.isMobileExact(mPhone)){
            Toast.makeText(this,"无效手机号！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mPhone)||TextUtils.isEmpty(mPassward)||TextUtils.isEmpty(mPassward2)){
            Toast.makeText(this,"请将信息填写完整！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!mPassward2.equals(mPassward)){
            Toast.makeText(this,"所输入的两次密码不一致！",Toast.LENGTH_SHORT).show();
            return;
        }


        //将数据写入数据库
        try{
            LitePal.getDatabase();
            User user = new User();
            user.setPhone(mPhone);
            user.setPassward(mPassward);
            user.save();
            Toast.makeText(this,"注册成功！",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }catch (Error error){
            error.printStackTrace();
        }

    }
}
