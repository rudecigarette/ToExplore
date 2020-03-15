package com.example.materialtest.utils;


import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.models.StoreClick;
import com.example.materialtest.models.StoreName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MysqlUtil {
    public static ArrayList<String> allPhones = new ArrayList<>();
    public static ArrayList<String> allPasswords = new ArrayList<>();
    public static Connection conn = null;
    public static int id = -1;
    public static String passwordFromSql = "";
    public static String shopname;
    public static int click;
    public static String Collection = null;
    public static boolean clickPlus(final String shopname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn == null) return;
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
                    System.out.println("点赞成功!");
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

        return true;
    }//点赞
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
    public static void getAllStoreClickandNameInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
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
                    System.out.println("Click,Name数据集更新完毕！");
        } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void getUserCollection(final String Username){
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
                    if(conn == null) return;
                    PreparedStatement sql = conn.prepareStatement("select collectshop from cumstomer where phone = ?");
                    sql.setString(1,Username);
                    ResultSet res = sql.executeQuery();
                    while (res.next()) {
                      Collection = res.getString(1);
                    }
                    System.out.println("Collection数据初始化完毕！"+ Collection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void collectOneStore(final String StoreName, final String Username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn == null) return;
                    Collection+="|"+StoreName+",";
                    PreparedStatement sql = conn.prepareStatement("update cumstomer set collectshop = ? where phone = ?");
                    sql.setString(1,Collection);
                    sql.setString(2,Username);
                    sql.executeUpdate();
                    System.out.println("Collection数据增添完毕！"+ Collection);
                }catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void uncollectOneStore(final String StoreName, final String Username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn == null) return;
                    Collection = removeSubString("|"+StoreName+",",Collection);
                    PreparedStatement sql = conn.prepareStatement("update cumstomer set collectshop = ? where phone = ?");
                    sql.setString(1,Collection);
                    sql.setString(2,Username);
                    sql.executeUpdate();
                    System.out.println("Collection数据删除完毕！"+ Collection);
                }catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void insertOneUser(final String phone, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(conn==null) System.out.println("conn为空！");
                    PreparedStatement sql = conn.prepareStatement("select count(*) from cumstomer");
                    ResultSet resultSet = sql.executeQuery();
                    while(resultSet.next()){
                        id = resultSet.getInt(1);
                    }
                    sql = conn.prepareStatement("insert into cumstomer(phone,password,id) values(?,?,?)");
                    sql.setString(1, phone);
                    sql.setString(2, password);
                    sql.setInt(3,id+1);
                    sql.executeUpdate();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void getAllPhoneAndPassword(){
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
                    if(conn == null) return;
                    PreparedStatement sql = conn.prepareStatement("select phone from cumstomer");
                    ResultSet res = sql.executeQuery();
                    while (res.next()) {
                        allPhones.add(res.getString(1));
                    }
                    System.out.println("allPhones数据初始化完毕！");

                    sql = conn.prepareStatement("select password from cumstomer");
                    res = sql.executeQuery();
                    while (res.next()) {
                        allPasswords.add(res.getString(1));
                    }
                    System.out.println("allPasswords数据初始化完毕！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static boolean validateUserInfo(final String phone , final String password){
        boolean result = false;
        int phoneindex = allPhones.indexOf(phone);
        if(allPasswords.get(phoneindex).equals(password)){
            result = true;
        }
        return result;
    }
   	public static String removeSubString(String zi,String zhu) {
        StringBuilder zhuchuan = new StringBuilder(zhu);
        int index = zhuchuan.indexOf(zi);
        if (index == -1) return null;
        zhuchuan.delete(index, index + zi.length());
        return zhuchuan.toString();
    }
}
