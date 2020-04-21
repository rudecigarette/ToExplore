package com.example.materialtest.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.materialtest.R;
import com.example.materialtest.adapter.FragmentAdapter;
import com.example.materialtest.fragment.CollectFindFragment;
import com.example.materialtest.fragment.CollectOtherFragment;
import com.example.materialtest.fragment.CollectStoreFragment;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.fragment.SecondFragment;
import com.example.materialtest.fragment.ThirdFragment;
import com.example.materialtest.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
    }

    private void initView() {
        initNavbar(true,"我的收藏");
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.CollectionViewPager);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
//        设置ViewPager
        viewPager.setOffscreenPageLimit(2);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CollectStoreFragment());
        final List<String> titles = new ArrayList<>();
        titles.add("商家");

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}
