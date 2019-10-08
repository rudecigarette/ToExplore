package com.example.materialtest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.materialtest.R;
import com.example.materialtest.Views.inputView;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

public class ChangePasswardActivity extends BaseActivity {

    private inputView mOldPassward,mPassward,mPasswardConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passward);
        initView();
    }
    public void initView(){
        initNavbar(true,"修改密码");
        mOldPassward = findViewById(R.id.chg_old_passward);
        mPassward = findViewById(R.id.chg_new_passward);
        mPasswardConfirm = findViewById(R.id.chg_new_passwardConfirm);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
    public void onConfirmClick(View v){
        String oldPassward = mOldPassward.getInputStr();
        String passward = mPassward.getInputStr();
        String passwardConfirm = mPasswardConfirm.getInputStr();
        boolean result = UserUtils.changePassward(this,oldPassward,passward,passwardConfirm);
        if(!result){
            return;
        }
        Toast.makeText(this,"密码修改成功",Toast.LENGTH_SHORT).show();
        UserUtils.Logout(this);
    }
}
