package com.example.materialtest.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.example.materialtest.activities.LoginActivity;

public class UserUtils {
    public static boolean vaildateLogin(Context context,String phone,String passward){
        //简单的
//        RegexUtils.isMobileSimple(phone);
        //精确的
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(passward)){
            Toast.makeText(context,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /*退出登录*/
    public static void Logout(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
