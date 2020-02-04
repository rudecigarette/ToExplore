package com.example.materialtest.db;

import android.content.Context;

import com.example.materialtest.utils.MysqlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbthread implements Runnable {
    private Context context;
    private String id;
    private String password;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void run() {
        try{
            MysqlUtil.getConnection("shop");
            Connection conn = MysqlUtil.conn;
            Thread.sleep(3000);
            if(conn!=null){
                System.out.println("conn非空！");
            }else{
                System.out.println("conn为空！");
            }
            PreparedStatement sql = conn.prepareStatement("insert into cumstomer(id,password) values(?,?)");
            sql.setString (1,id);
            sql.setString (2,password);
            sql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
