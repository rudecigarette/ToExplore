package com.example.materialtest.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.materialtest.R;
import com.example.materialtest.adapter.FragmentAdapter;
import com.example.materialtest.fragment.FirstFragment;
import com.example.materialtest.fragment.SecondFragment;
import com.example.materialtest.fragment.ThirdFragment;
import com.example.materialtest.utils.NoScrollViewPager;
import com.example.materialtest.utils.StatusBarUtils;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView bnv;
    private NoScrollViewPager mViewPager;
    public static AppBarLayout mAppBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化主活动布局
     */
    public void initView(){
//        获取实例
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        mAppBarLayout = findViewById(R.id.appbar_layout);
//        设置打开抽屉按钮
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

//         底部导航栏点击事件
        bnv = findViewById(R.id.bottom_nav_view);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                switch (menuId) {
                    case R.id.tab_one:
                        mAppBarLayout.setExpanded(true);
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_two:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_three:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
//         设置抽屉按钮点击事件
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

//        设置viewpager
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setNoScroll(true);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());

        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
//         ViewPager 滑动事件监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
                mAppBarLayout.setExpanded(true);
                bnv.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
//        设置主活动的状态栏颜色
    StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    /**
     *设置ActionBar展开栏样式
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
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
                startActivity(new Intent(this,Test.class));
                break;
            default:
        }
        return true;
    }
}
