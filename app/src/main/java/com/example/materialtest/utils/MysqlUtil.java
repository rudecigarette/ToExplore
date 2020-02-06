package com.example.materialtest.utils;

import android.os.StrictMode;

import com.example.materialtest.activities.MainActivity;
import com.example.materialtest.activities.StoreActivity;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.models.Store;
import com.example.materialtest.models.StoreClick;
import com.example.materialtest.models.StoreInfo;
import com.example.materialtest.models.StoreName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MysqlUtil {
    public static Connection conn = null;
    public static void mysqltest() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn =(Connection) DriverManager.getConnection("jdbc:mysql://134.175.231.43:3306/shop?serverTimezone=UTC", "hss", "123456");
                    System.out.println("shop数据库连接成功");
                    System.out.println("查询数据库中shop1表的内容");
                    System.out.println(conn==null);
                    PreparedStatement sql = conn.prepareStatement("select * from shop1");
                    ResultSet resultset = sql.executeQuery();
                    while (resultset.next()) {
                        int id = resultset.getInt(1);
                        String name = resultset.getString(2);
                        System.out.println(id + " " + name);

                    }
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }).start();


    }//测试成功，jar用5.0的就好啦
    public static int clickcount = 0;
    private static ArrayList<StoreInfo> storeInfos = new ArrayList<>();
    public static int id;
    public static String shopname;
    public static float star;
    public static String typename;
    public static String Ename;
    public static String comment;
    public static int click;


    public static boolean clickPlus(final String shopname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = getConnection("shop");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    if(conn == null) System.out.println("conn为空！！！");
                    PreparedStatement sql2=conn.prepareStatement("select * from shop1 where shopname=?");
                    sql2.setString(1,shopname);   //获取id为3(k)的商家的信息
                    ResultSet res=sql2.executeQuery();
                    int i = -1;
                    while(res.next())
                    {
                         i=res.getInt("click");             //获取该商家原本的点赞数click
                    }
                    PreparedStatement sql=conn.prepareStatement("update shop1 set click=? where shopname =?");
                    sql.setInt(1,++i); //第一个参数(click)设置为++i，点赞数+1
                    sql.setString(2,shopname); //第二个参数(id)为该商家的id 3(k)
                    sql.executeUpdate();
                    System.out.println("操作成功!");
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return true;
    }//点赞

    public static void getClick(final String shopname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = getConnection("shop");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                    if(conn == null) System.out.println("conn为空！！！");
                    PreparedStatement sql2=conn.prepareStatement("select * from shop1 where shopname=?");
                    sql2.setString(1,shopname);   //获取id为3(k)的商家的信息
                    ResultSet res=sql2.executeQuery();
                    while(res.next())
                    {
                        clickcount=res.getInt("click");//获取该商家原本的点赞数click
                    }
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//获取点赞数

    public static Connection getConnection(final String DBName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("数据库驱动加载成功");
                } catch (ClassNotFoundException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                try {
                    conn =(Connection) DriverManager.getConnection("jdbc:mysql://134.175.231.43:3306/"+DBName+"?serverTimezone=UTC", "hss", "123456");
                    System.out.println("数据库连接成功");
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }).start();

        return conn;

    }//获取和数据库的连接
/**
 * 从数据库获取所有商家信息
 */
    public static void getAllStoreClickInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = getConnection("shop");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    PreparedStatement sql = conn.prepareStatement("select click from shop1");
                    ResultSet res = sql.executeQuery();
                    FirstFragment.allStoreClickInfos.clear();
                    while (res.next()) {
                        click = res.getInt(1);
                        FirstFragment.allStoreClickInfos.add(new StoreClick(click));
                    }
                    sql = conn.prepareStatement("select shopname from shop1");
                    res = sql.executeQuery();
                    while(res.next()){
                        shopname = res.getString(1);
                        FirstFragment.allStoreNameInfos.add(new StoreName(shopname));
                    }
                    System.out.println("数据集初始化完毕！");
        } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }


    public static void insertOneUser(final String phone, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = getConnection("shop");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(conn==null) System.out.println("conn为空！");
                    PreparedStatement sql = conn.prepareStatement("insert into cumstomer(phone,password) values(?,?)");
                    sql.setString(1, phone);
                    sql.setString(2, password);
                    sql.executeUpdate();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
