package com.example.materialtest.helps;

import com.example.materialtest.db.User;

/**
 * 1.用户登录
 *      1.当用户登录时，利用SharedPreferences保存登录用户的用户标记（手机号码）
 *      2.利用全局单例类UserHelper保存登录用户信息
 *          1.用户登录
 *          2.用户打开应用程序，检测SharedPreferences中是否存在用户标记
 *          如果存在则为UserHelper赋值，并且进入主页。如果不存在，则进入登录页面
 * 2.用户退出
 *      1.删除掉SP中保存的用户登录标记
 *      2.退出到登录页面
 */
public class UserHelp {

        private static UserHelp instance;

        private UserHelp(){}

        public static UserHelp getInstance(){
            if(instance==null){
                synchronized (UserHelp.class){
                    if(instance==null){
                        instance = new UserHelp();
                    }
                }
            }
            return instance;
        }
        private String phone = null;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
}
