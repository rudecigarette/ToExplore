package com.example.materialtest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.materialtest.MainActivity;
import com.example.materialtest.R;
import com.example.materialtest.activities.shareActivity;
import com.example.materialtest.adapter.FruitAdapter;
import com.example.materialtest.models.Share;
import com.example.materialtest.models.Store;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.ReadtxtUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class SecondFragment extends Fragment {
    @Nullable
    private List<Share> shares = new ArrayList<>();


    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

    private Button shareButton = null;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
            swipeRefresh = view.findViewById(R.id.swipe_refresh);
            swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshFruits();
                }
            });
            shareButton = view.findViewById(R.id.shareBtn);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),shareActivity.class);
                    startActivity(intent);
                }
            });
            initFruits();
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//            GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new FruitAdapter(shares);
            recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));
        return view;
    }
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MysqlUtil.getShares();
                        shares = FirstFragment.shares;
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    private void initFruits() {
            shares = FirstFragment.shares;
    }
}

