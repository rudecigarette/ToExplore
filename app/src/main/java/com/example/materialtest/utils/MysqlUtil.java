package com.example.materialtest.utils;


import com.baidu.mapapi.common.SysOSUtil;
import com.example.materialtest.activities.MainActivity;
import com.example.materialtest.activities.StoreActivity;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.models.Share;
import com.example.materialtest.models.StoreClick;
import com.example.materialtest.models.StoreDetail;
import com.example.materialtest.models.StoreName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import io.realm.annotations.PrimaryKey;

public class MysqlUtil {
    public static ArrayList<String> allPhones = new ArrayList<>();
    public static ArrayList<String> allPasswords = new ArrayList<>();
    public static Connection conn = null;
    public static int id = -1;
    public static String passwordFromSql = "";
    public static String shopname;
    public static int click;
    public static String Collection = null;
    public static String buy = null;
    public static String lookshop = null;
    public static int UserId;
    public static int UserId2;
    public static int UserId3;
    public static String LabelRec=null;
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
        if(conn==null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    conn = getConnection("shop");
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(conn == null) return;
                    PreparedStatement sql = conn.prepareStatement("select phone from cumstomer");
                    ResultSet res = sql.executeQuery();
                    allPhones.clear();
                    while (res.next()) {
                        allPhones.add(res.getString(1));
                    }
                    System.out.println("allPhones数据初始化完毕！");
                    System.out.println(allPhones.size());
                    sql = conn.prepareStatement("select password from cumstomer");
                    res = sql.executeQuery();
                    allPasswords.clear();
                    while (res.next()) {
                        allPasswords.add(res.getString(1));
                    }
                    System.out.println("allPasswords数据初始化完毕！");
                    System.out.println(allPasswords.size());

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
    public static void choseLabel(final String p,final String userPhone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (conn == null) return;
                    String s="";
                    PreparedStatement sql3=conn.prepareStatement("select * from cumstomer where phone=?");
                    sql3.setString(1,userPhone);   //假设当前与用户id为1，获取id为1的用户的信息
                    ResultSet res=sql3.executeQuery();
                    while(res.next())
                    {
                        s=res.getString("label");             //获取该用户已选的标签内容
                    }
                    PreparedStatement sql=conn.prepareStatement("update cumstomer set label=? where phone =?");
                    sql.setString(1,s+"|"+p); //第一个参数(标签)设置为s+p
                    sql.setString(2,userPhone); //第二个参数(id)为该用户的id
                    sql.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void getStoreDetails(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    PreparedStatement sql = conn.prepareStatement("select * from shop_detail_info");
                    ResultSet res = sql.executeQuery();
                    FirstFragment.storeDetails.clear();
                    while (res.next()) {
                        String storePhone = res.getString(4);
                        String openTime = res.getString(3);
                        String shopDetail = res.getString(5);
                        FirstFragment.storeDetails.add(new StoreDetail(storePhone,openTime,shopDetail));
                    }
                    System.out.println("storeDetails数据集更新完毕！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void publishSharing(final String userId, final String shopname, final String title, final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    int id =0;
                    PreparedStatement sql=conn.prepareStatement("select * from strategy");
                    ResultSet res=sql.executeQuery();
                    while (res.next()) {
                        id=res.getInt("textid");   //获取上一个攻略的编号
                    }
                    id++;
                    sql=conn.prepareStatement("insert into strategy values(?,?,?,?,?)");
                    sql.setInt(1,id); //第一个参数为攻略编号
                    sql.setString(2,userId); //第二个参数用户的id，假设当前用户id为1
                    sql.setString(3,shopname);  //设置评价的店家名称
                    sql.setString(4,title);    //设置标题
                    sql.setString(5,text);     //设置内容
                    sql.executeUpdate();
                    System.out.println("上传share数据完毕！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void getShares(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    int id =0;
                    PreparedStatement sql=conn.prepareStatement("select * from strategy");
                    ResultSet res=sql.executeQuery();
                    FirstFragment.shares.clear();
                    while (res.next()) {
                        String uid=res.getString("uid");
                        String shopname=res.getString("shopname");
                        String title=res.getString("title");
                        String text=res.getString("text");
                        FirstFragment.shares.add(new Share(text,title,"用户"+uid,shopname));
                    }

                    System.out.println("shares数据集更新完毕！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();

    }
    public static void addLookShop(final String phoneNum, final String storeId){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    PreparedStatement sql=conn.prepareStatement("select lookshop from cumstomer where phone = ?");
                    sql.setString(1,phoneNum);
                    ResultSet res=sql.executeQuery();
                    while (res.next()) {
                        lookshop=res.getString("lookshop");
                    }
                    int isExist = lookshop.indexOf("|"+storeId+",");
                    if(isExist==-1){
                        lookshop +="|"+storeId+",";
                    }

                    sql=conn.prepareStatement("update cumstomer set lookshop = ? where phone = ?");
                    sql.setString(1,lookshop);
                    sql.setString(2,phoneNum);
                    sql.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static String getLabelRec(final String phoneNum){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    PreparedStatement sql=conn.prepareStatement("select id from cumstomer where phone = ?");
                    sql.setString(1,phoneNum);
                    ResultSet res=sql.executeQuery();
                    while (res.next()) {
                        UserId=res.getInt("id");
                        System.out.println(UserId);
                    }
                    sql=conn.prepareStatement("select rec from customerlabelrec where uid =?");
                    sql.setString(1,UserId+"");
                    res = sql.executeQuery();
                    while(res.next()){
                        StoreActivity.LabelRec = res.getString(1);
                        System.out.println(StoreActivity.LabelRec);
                    }
                    System.out.println("刷新labelRec成功！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();

        return LabelRec;
    }
    public static void getHistoryRec(final String phoneNum){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    PreparedStatement sql=conn.prepareStatement("select id from cumstomer where phone = ?");
                    sql.setString(1,phoneNum);
                    ResultSet res=sql.executeQuery();
                    while (res.next()) {
                        UserId2=res.getInt("id");
                    }
                    sql=conn.prepareStatement("select s_sid from nmf_rec where uid =?");
                    sql.setString(1,UserId2+"");
                    res = sql.executeQuery();
                    FirstFragment.historyRec.clear();
                    while(res.next()){
                        int sid = (int)res.getDouble(1)-1;
                        FirstFragment.historyRec.add (FirstFragment.stores.get(sid));
                    }
                    System.out.println("刷新historyRec成功！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void getGuessyoulikeRec(final String phoneNum){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    PreparedStatement sql=conn.prepareStatement("select id from cumstomer where phone = ?");
                    sql.setString(1,phoneNum);
                    ResultSet res=sql.executeQuery();
                    while (res.next()) {
                        UserId3=res.getInt("id");
                    }
                    sql=conn.prepareStatement("select shopid from slop_rec where uid =?");
                    sql.setString(1,UserId3+"");
                    res = sql.executeQuery();
                    FirstFragment.guessyoulikeRec.clear();
                    while(res.next()){
                        int sid = (int)res.getDouble(1)-1;
                        FirstFragment.guessyoulikeRec.add (FirstFragment.stores.get(sid));
                    }
                    System.out.println("刷新guessyoulikeRec成功！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();


    }
    public static void buyOneStore(final String StoreName, final String Username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn == null) return;
                    PreparedStatement sql = conn.prepareStatement("select buyshop from cumstomer where phone = ?");
                    sql.setString(1,Username);
                    ResultSet resultSet = sql.executeQuery();
                    while(resultSet.next()){
                        buy = resultSet.getString("buyshop");
                    }
                    buy+="|"+StoreName+",";
                    sql = conn.prepareStatement("update cumstomer set buyshop = ? where phone = ?");
                    sql.setString(1,buy);
                    sql.setString(2,Username);
                    sql.executeUpdate();
                    System.out.println("buy数据增添完毕！"+ buy);
                }catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
    public static void getUserLabel(final String UserPhone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(conn==null) return;
                    PreparedStatement sql=conn.prepareStatement("select label from cumstomer where phone = ?");
                    sql.setString(1,UserPhone);
                    ResultSet res=sql.executeQuery();

                    while (res.next()) {
                        MainActivity.UserLabel = res.getString("label");
                    }

                    System.out.println("userLabel数据更新完毕！"+MainActivity.UserLabel);
                } catch (SQLException e) {
                    e.printStackTrace();
                }}}).start();
    }
}
