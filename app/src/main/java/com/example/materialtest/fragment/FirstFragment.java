package com.example.materialtest.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.materialtest.R;
import com.example.materialtest.activities.MainActivity;
import com.example.materialtest.adapter.StoreAdapter;
import com.example.materialtest.models.Store;
import com.example.materialtest.models.StoreClick;
import com.example.materialtest.models.StoreInfo;
import com.example.materialtest.models.StoreName;
import com.example.materialtest.utils.LocateUtil;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.ReadtxtUtil;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    public static ArrayList<StoreClick> allStoreClickInfos = new ArrayList<>();
    public static ArrayList<StoreName> allStoreNameInfos = new ArrayList<>();
    public static MapView mMapView = null;
    public static LocationClient mLocationClient;
    public static LocationClient yLocationClient;
    private TextView cardViewText;
    private TextView supInfo;
    private PoiSearch mPoiSearch;
    public boolean isExpanded;
    public static BaiduMap baiduMap;
    private LocationClientOption locationOption;
    private boolean isFirstLocate = true;
    private View view;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    RecyclerView recyclerView;
    StoreAdapter myAdapter;
    public static ArrayList<Store> stores = new ArrayList<>();
    public static ArrayList<StoreInfo> storeInfos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        initView();
        checkLocationPermission();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MysqlUtil.getAllStoreClickInfo();
        /**
         *权限申请
         */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.
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

    public void initData() {
        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        String storeName = "";
        String storeInfo = "";
        String storePic = "";
        int resourceId;
        List<String> data = ReadtxtUtil.getString(inputStream);
        Context ctx = getContext();


        for (int i = 0; i < data.size(); i++) {
            storeName = data.get(i).split(",")[1];
            storeInfo = "评分：" + data.get(i).split(",")[2] + "  " + data.get(i).split(",")[3];
            storePic = data.get(i).split(",")[4];
            resourceId = getResources().getIdentifier(storePic, "drawable", ctx.getPackageName());
            Store store = new Store(storeName, storeInfo, resourceId);
            stores.add(store);
        }
    }

    public void initView() {

        initData();
        recyclerView = view.findViewById(R.id.myfirstRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new StoreAdapter();
        myAdapter.setStores(stores);
        myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);

        slidingUpPanelLayout = view.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setScrollableView((view.findViewById(R.id.myfirstRecycleView)));
        slidingUpPanelLayout.setAnchorPoint(0.75f);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    isExpanded = true;
                    MainActivity.mAppBarLayout.setExpanded(false);

                }

                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    isExpanded = false;
                    MainActivity.mAppBarLayout.setExpanded(true);
                }
            }
        });


        mMapView = view.findViewById(R.id.bmapView);
        cardViewText = view.findViewById(R.id.cardview_text);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        //        设置FAB点击事件,回到当前位置
        FloatingActionButton fab = view.findViewById(R.id.backhere);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHere();
                Snackbar.make(view, "已定位到当前位置", Snackbar.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab2 = view.findViewById(R.id.showSUP);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
            }
        });

    }

    private void checkLocationPermission() {
        if (!LocateUtil.isLocServiceEnable(getActivity().getApplicationContext())) {//检测是否开启定位服务
            //未开启定位服务的操作
            new AlertDialog.Builder(FirstFragment.this.getContext())
                    .setTitle("定位服务未开启")
                    .setMessage("请先开启定位服务以允许应用访问您的位置")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

                        }
                    }).show();
        } else {//检测用户是否将当前应用的定位权限拒绝
            int checkResult = LocateUtil.checkOp(getActivity().getApplicationContext(), 2, AppOpsManager.OPSTR_FINE_LOCATION);//其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
            int checkResult2 = LocateUtil.checkOp(getActivity().getApplicationContext(), 1, AppOpsManager.OPSTR_FINE_LOCATION);
            if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
                //未开启定位权限或者被拒绝的操作
                new AlertDialog.Builder(FirstFragment.this.getContext())
                        .setTitle("应用定位权限未授权")
                        .setMessage("请先授权应用定位以允许应用访问您的位置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 转到手机设置界面，用户设置GPS
                                Intent intent = new Intent();
                                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                                startActivity(intent);
                            }
                        }).show();
            }
        }
    }
    /**
     * 与地图有关的操作
     */
    //    新建位置监听器，并设立监听成功事件
    public class yLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus mMapStatus = new MapStatus.Builder().target(ll).zoom(18).build();
            MapStatusUpdate newupdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            baiduMap.animateMapStatus(newupdate);
        }
    }

    //    回到当前位置
    public void backHere() {
        yLocationClient = new LocationClient(this.getActivity());
        yLocationClient.registerLocationListener(new yLocationListener());
        locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("bd09ll");
        yLocationClient.setLocOption(locationOption);
        yLocationClient.start();
        if (yLocationClient.isStarted()) {
            yLocationClient.stop();
        }
    }

    public void controlMyMap() {
        mLocationClient = new LocationClient(this.getActivity());//????getapplicationcontext
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
//                searchPoi(location);
            }
            MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
            locationBuilder.latitude(location.getLatitude());
            locationBuilder.longitude(location.getLongitude());
            MyLocationData locationData = locationBuilder.build();
            baiduMap.setMyLocationData(locationData);
//            searchPoi(location);
        }

        /**
         * 回到当前位置
         */
//    新建位置监听器，并设立监听成功事件
        public class yLocationListener implements BDLocationListener {
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

    public static ArrayList<Store> getStores() {

        return stores;
    }

}

