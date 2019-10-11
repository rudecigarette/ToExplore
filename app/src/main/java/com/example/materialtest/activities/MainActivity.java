package com.example.materialtest.activities;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.materialtest.R;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private MapView mMapView=null;
    private TextView muserName;
    public LocationClient mLocationClient;
    public LocationClient yLocationClient;
    private TextView cardViewText;
    private BaiduMap baiduMap;
    private LocationClientOption locationOption;
    private boolean isFirstLocate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /**
         *权限申请
         */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            controlMyMap();
            requestLocation();
        }


    }
    /**
     * 初始化主活动布局
     */
    public void initView(){
        mMapView=findViewById(R.id.bmapView);
        cardViewText=findViewById(R.id.cardview_text);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.me:
                        Intent intent_toMe = new Intent(MainActivity.this,MeActivity.class);
                        startActivity(intent_toMe);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
//        设置FAB点击事件,回到当前位置
        FloatingActionButton fab = findViewById(R.id.backhere);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHere();
                Snackbar.make(view, "已定位到当前位置", Snackbar.LENGTH_SHORT) .show();
            }
        });
    }
    /**
     *设置ActionBar展开栏样式
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    /**
     * 与地图有关的操作
     */
    public void controlMyMap(){
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatus mMapStatus = new MapStatus.Builder().target(ll).zoom(18).build();
                    MapStatusUpdate newupdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append(location.getLocationDescribe().toString()).append("\n");
                    currentPosition.append("纬度：").append(location.getLatitude()).
                            append("\n");
                    currentPosition.append("经线：").append(location.getLongitude()).
                            append("\n");
                    currentPosition.append("定位方式：");
                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
                        navigateTo(location);
                        currentPosition.append("GPS");
                    } else if (location.getLocType() ==
                            BDLocation.TypeNetWorkLocation) {
                        navigateTo(location);
                        currentPosition.append("网络");
                    }
                    cardViewText.setText(currentPosition);
                }
            });
        }
    }
    private void requestLocation() {
        locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("bd09ll");
        locationOption.setScanSpan(5000);
        locationOption.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(locationOption);
        mLocationClient.start();
    }
    /**
     * 导航
     */
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
    /**
     * 回到当前位置
     */
//    新建位置监听器，并设立监听成功事件
    public class yLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus mMapStatus = new MapStatus.Builder().target(ll).zoom(18).build();
            MapStatusUpdate newupdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            baiduMap.animateMapStatus(newupdate);
        }
    }
    public void backHere(){
        yLocationClient = new LocationClient(getApplicationContext());
        yLocationClient.registerLocationListener(new yLocationListener());
        locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("bd09ll");
        yLocationClient.setLocOption(locationOption);
        yLocationClient.start();
        if(yLocationClient.isStarted()){
            yLocationClient.stop();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    controlMyMap();
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    /**
     * ActionBar选项点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);//打开抽屉
                }
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    /**
     * 以下为地图生命周期管理
     */

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        yLocationClient.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
