//package com.example.materialtest.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class MysqlUtil {
//    public static ResultSet connectMysqlDB() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("数据库驱动加载成功");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://134.175.231.43:3306/shop?useSSL=true&serverTimezone=GMT", "hss", "123456");//url地址
//            System.out.println("shop数据库连接成功");
//            System.out.println("查询数据库中shop1表的内容");
//            PreparedStatement sql = conn.prepareStatement("select * from shop1");
//            ResultSet resultset = sql.executeQuery();
//            while (resultset.next()) {
//                int id = resultset.getInt(1);
//                String name = resultset.getString(2);
//                System.out.println(id + " " + name);
//
//            }
//            return resultset;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
