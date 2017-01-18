package com.cetcme.shipmonitor;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cetcme.shipmonitor.Fragment.ShipsFragment;
import com.cetcme.shipmonitor.Fragment.SettingFragment;
import com.cetcme.shipmonitor.Fragment.ListFragment;
import com.cetcme.shipmonitor.Fragment.InfoFragment;
import com.qiuhong.qhlibrary.QHTitleView.QHTitleView;

public class MainActivity extends AppCompatActivity {

    private ShipsFragment mShipsFragment;
    private ListFragment mListFragment;
    private InfoFragment mInfoFragment;
    private SettingFragment mSettingFragment;

    private QHTitleView qhTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initTitleView();
        initBottomView();
    }

    private void initTitleView() {
        qhTitleView = (QHTitleView) findViewById(R.id.main_QHTitleView);
        qhTitleView.setTitle("main");
        qhTitleView.setBackView(0);
        qhTitleView.setRightView(R.drawable.add_button);
        qhTitleView.setClickCallback(new QHTitleView.ClickCallback() {
            @Override
            public void onBackClick() {
                //
            }

            @Override
            public void onRightClick() {
                Log.i("11", "onRightClick: 111");
                Intent intent = new Intent(getApplication(), TestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBottomView() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.tab1, getString(R.string.fragment_name_1)))
                .addItem(new BottomNavigationItem(R.drawable.tab2, getString(R.string.fragment_name_2)))
                .addItem(new BottomNavigationItem(R.drawable.tab3, getString(R.string.fragment_name_3)))
                .addItem(new BottomNavigationItem(R.drawable.tab4, getString(R.string.fragment_name_4)))
                .initialise();//所有的设置需在调用该方法前完成

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {//这里也可以使用SimpleOnTabSelectedListener
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
//                Log.d("Main", "onTabSelected() called with: " + "position = [" + position + "]");
                FragmentManager fm = getFragmentManager();
                //开启事务
                FragmentTransaction transaction = fm.beginTransaction();
                switch (position) {
                    case 0:
                        if (mShipsFragment == null) {
                            mShipsFragment = ShipsFragment.newInstance(getString(R.string.fragment_name_1));
                        }
                        qhTitleView.setTitle(getString(R.string.fragment_name_1));
                        transaction.replace(R.id.tabs, mShipsFragment);
                        break;
                    case 1:
                        if (mListFragment == null) {
                            mListFragment = ListFragment.newInstance(getString(R.string.fragment_name_2));
                        }
                        qhTitleView.setTitle(getString(R.string.fragment_name_2));
                        transaction.replace(R.id.tabs, mListFragment);
                        break;
                    case 2:
                        if (mInfoFragment == null) {
                            mInfoFragment = InfoFragment.newInstance(getString(R.string.fragment_name_3));
                        }
                        qhTitleView.setTitle(getString(R.string.fragment_name_3));
                        transaction.replace(R.id.tabs, mInfoFragment);
                        break;
                    case 3:
                        if (mSettingFragment == null) {
                            mSettingFragment = SettingFragment.newInstance(getString(R.string.fragment_name_4));
                        }
                        qhTitleView.setTitle(getString(R.string.fragment_name_4));
                        transaction.replace(R.id.tabs, mSettingFragment);
                        break;
                    default:
                        break;
                }
                // 事务提交
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
//                Log.i("main", "onTabUnselected: " + position);
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
//                Log.i("main", "onTabReselected: " + position);
            }
        });

        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mShipsFragment = ShipsFragment.newInstance(getString(R.string.fragment_name_1));
        qhTitleView.setTitle(getString(R.string.fragment_name_1));
        transaction.replace(R.id.tabs, mShipsFragment);
        transaction.commit();
    }
}
