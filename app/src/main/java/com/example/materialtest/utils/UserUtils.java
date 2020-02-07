package com.example.materialtest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.materialtest.R;
import com.example.materialtest.activities.LoginActivity;
import com.example.materialtest.helps.RealmHelper;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.models.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class UserUtils {
    /**
      *验证用户登录
      */
    public static boolean validateLogin(Context context,String phone,String passward){
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
        /**
         * 1.用户当前输入手机号是否已被注册
         * 2.若已注册，用户的输入的手机号是否匹配
         */
        if(!UserUtils.userExitsFromPhone(phone)){
            Toast.makeText(context,"当前输入手机号未注册",Toast.LENGTH_SHORT).show();
            return false;
        }
        RealmHelper realmHelper = new RealmHelper();
        boolean result = realmHelper.validateUser(phone,passward);
        realmHelper.close();
        if(!result){
            Toast.makeText(context,"当前输入手机号或密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }
//        保存用户登录标记
        boolean isSave = SPUtils.saveUser(context, phone);
        if(!isSave){
            Toast.makeText(context,"系统错误，请稍后重试",Toast.LENGTH_SHORT).show();
            return false;
        }
//         保存用户标记，在全局单例类之中
        UserHelp.getInstance().setPhone(phone);
        return true;
    }
    /*
    **退出登录
    */
    public static void Logout(Context context){
//        删除SP用户标记
        boolean isRemove = SPUtils.removeUser(context);
        if(!isRemove){
            Toast.makeText(context,"系统错误，请稍后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        //添加Intent标志符,清理task栈并创建新栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }
    /*
    * 注册用户
    * */
    public static boolean registerUser(Context context, String phone, String passward, String passwardConfim){
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(StringUtils.isEmpty(passward)||StringUtils.isEmpty(passwardConfim)){
            Toast.makeText(context,"请确认密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!passward.equals(passwardConfim)){
            Toast.makeText(context,"两次所输入的密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
//        判断用户当前输入手机号是否已被注册
        /**
         * 1.通过realm获取所有已注册的用户信息
         * 2.查询用户输入的手机号是否已经注册
         */
        if(UserUtils.userExitsFromPhone(phone)){
            Toast.makeText(context,"该手机号已注册",Toast.LENGTH_SHORT).show();
            return false;
        }
//       通过验证，将数据存入数据库
        UserModel userModel = new UserModel();
        userModel.setPassward(EncryptUtils.encryptMD5ToString(passward));
        userModel.setPhone(phone);
        saveUser(userModel);
        //将注册信息存入数据库
        MysqlUtil.insertOneUser(phone,passward);
        return true;
    }
    /*
    * 保存用户到数据库
    * */
    public static void saveUser(UserModel userModel){
        RealmHelper realmHelper = new RealmHelper();
        realmHelper.saveUser(userModel);
        realmHelper.close();
    }
    /**
     * 判断手机号是否已经存在
     */
    public static boolean userExitsFromPhone(String phone){
        boolean result = false;
        ArrayList<String> allPhones = MysqlUtil.allPhones;
        for(String p : allPhones){
            if(p.equals(phone)){
                result = true;
                break;
            }
        }
        return result;
    }
    /**
     * 验证是否存在已登录用户
     */
    public static boolean validateUserLogin(Context context){
        return SPUtils.isLoginUser(context);
    }
    /**
     * 1.用户密码修改确认
     *      1.验证是否输入原密码
     *      2.验证新密码输入与新密码确认是否相同
     *      3.验证原密码输入是否与旧密码相同
     * 2.利用realm的模型自动更新功能完成密码的修改
     */
    public static boolean changePassward(Context context,String oldPassward,String passward,String passwardConfirm){
        if(TextUtils.isEmpty(oldPassward)){
            Toast.makeText(context,"请输入原密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(passward)||TextUtils.isEmpty(passwardConfirm)){
            Toast.makeText(context,"请确认新密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!passward.equals(passwardConfirm)){
            Toast.makeText(context,"两次所输入的新密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
//        验证原密码是否正确
        RealmHelper realmHelper = new RealmHelper();
        UserModel userModel = realmHelper.getUser();
        if(!EncryptUtils.encryptMD5ToString(oldPassward).equals(userModel.getPassward())){
            Toast.makeText(context,"原密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }
        realmHelper.changePassward(EncryptUtils.encryptMD5ToString(passward));
        realmHelper.close();
        return true;
    }
}
