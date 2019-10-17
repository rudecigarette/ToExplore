package com.example.materialtest.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.materialtest.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    public static MapView mMapView=null;
    public static LocationClient mLocationClient;
    public static LocationClient yLocationClient;
    private TextView cardViewText;
    public static BaiduMap baiduMap;
    private LocationClientOption locationOption;
    private boolean isFirstLocate = true;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        initView();

        /**
         *权限申请
         */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this.getActivity(),Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(this.getActivity(), permissions, 1);
        } else {
            controlMyMap();
            requestLocation();
        }





        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this.getActivity(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    controlMyMap();
                    requestLocation();
                } else {
                    Toast.makeText(this.getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }
    }
    public void requestLocation() {
        locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("bd09ll");
        locationOption.setScanSpan(5000);
        locationOption.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(locationOption);
        mLocationClient.start();
    }
    public void initView(){
        mMapView=view.findViewById(R.id.bmapView);
        cardViewText=view.findViewById(R.id.cardview_text);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //        设置FAB点击事件,回到当前位置
        FloatingActionButton fab = view.findViewById(R.id.backhere);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHere();
                Snackbar.make(view, "已定位到当前位置", Snackbar.LENGTH_SHORT) .show();
            }
        });

    }
    /**
     * 与地图有关的操作
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
//    回到当前位置
    public void backHere(){
        yLocationClient = new LocationClient(this.getActivity());
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

    public  void controlMyMap(){
        mLocationClient=new LocationClient(this.getActivity());//????getapplicationcontext
        mLocationClient.registerLocationListener(new MyLocationListener());
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {

            getActivity().runOnUiThread(new Runnable() {
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

        /**
         * 导航
         */
        public void navigateTo(BDLocation location) {
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
    }
    /**
     * 地图生命周期管理
     */
    @Override
    public void onResume() {
        super.onResume();
        backHere();
        mMapView.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        yLocationClient.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}

