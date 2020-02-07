package com.example.materialtest.helps;

import com.example.materialtest.models.UserModel;
import com.example.materialtest.utils.MysqlUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {
    private Realm mRealm;
    public RealmHelper(){
        mRealm = Realm.getDefaultInstance();
    }
    /**
     * 使用完Realm后及时释放，关闭数据库
     */
    public void close(){
        if(mRealm!=null && !mRealm.isClosed()){
            mRealm.close();
        }
    }
    /**
     * 保存用户信息
     */
    public void saveUser(UserModel userModel){
        mRealm.beginTransaction();
        mRealm.insert(userModel);
//        mRealm.insertOrUpdate(userModel);
        mRealm.commitTransaction();
    }
    /**
     * 返回所有用户
     */
    public List<UserModel> getAllUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }
    /**
     * 验证用户信息
     */
    public boolean validateUser(String phone,String passward){
        boolean mresult = false;
        mresult = MysqlUtil.validateUserInfo(phone,passward);
        return mresult;
    }
    /**
     * 获取当前用户
     */
    public UserModel getUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone",UserHelp.getInstance().getPhone()).findFirst();
        return userModel;
    }
    /**
     * 修改用户密码
     */
    public void changePassward(String passward){
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassward(passward);
        mRealm.commitTransaction();
    }

}
