package com.cetcme.shipmonitor;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetcme.shipmonitor.Fragment.OfflineManagerFragment;
import com.qiuhong.qhlibrary.QHTitleView.QHTitleView;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoActivity extends FragmentActivity {

    private String TAG = "MoreInfoActivity";

    /**
     * 顶部LinearLayout个数
     */
    private int tabNumber = 4;

    /**
     * 顶部四个LinearLayout
     */
    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout tab3;
    private LinearLayout tab4;

    /**
     * 顶部四个TextView
     */
    private TextView tab1_text;
    private TextView tab2_text;
    private TextView tab3_text;
    private TextView tab4_text;

    /**
     * Tab的那个引导线
     */
    private ImageView mTabLine;

    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    private Resources res;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        res = getResources();
        title = getIntent().getExtras().getString("title");
        initNavigationView();
        initViewPager();

    }

    public void onResume() {
        super.onResume();
    }
    public void onPause() {
        super.onPause();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private QHTitleView navigationView;

    private void initNavigationView() {
        navigationView = (QHTitleView) findViewById(R.id.nav_main_in_sign_activity);
        navigationView.setTitle(title);
        navigationView.setBackView(R.drawable.icon_back_button);
        navigationView.setRightView(0);
        navigationView.setClickCallback(new QHTitleView.ClickCallback() {

            @Override
            public void onRightClick() {
                Log.i("main","点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {
                Log.i("main","点击了左侧按钮!");
                onBackPressed();
            }
        });
    }

    private void initViewPager() {

        initView();

        mViewPager = (ViewPager) findViewById(R.id.viewpager_in_sign_activity);

        /**
         * 初始化Adapter
         */
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
        mViewPager.addOnPageChangeListener(new TabOnPageChangeListener());

        initTabLine();
    }


    /**
     * 根据屏幕的宽度，初始化引导线的宽度
     */
    private void initTabLine() {
        mTabLine = (ImageView) findViewById(R.id.id_tab_line);

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        layoutParams.width = screenWidth / tabNumber;//设置该控件的layoutParams参数
        mTabLine.setLayoutParams(layoutParams);//将修改好的layoutParams设置为该控件的layoutParams
    }

    /**
     * 初始化控件，初始化Fragment
     */
    private void initView() {
        tab1_text = (TextView) findViewById(R.id.tab1_text);
        tab2_text = (TextView) findViewById(R.id.tab2_text);
        tab3_text = (TextView) findViewById(R.id.tab3_text);
        tab4_text = (TextView) findViewById(R.id.tab4_text);

        tab1_text.setOnClickListener(new TabOnClickListener(0));
        tab2_text.setOnClickListener(new TabOnClickListener(1));
        tab3_text.setOnClickListener(new TabOnClickListener(2));
        tab4_text.setOnClickListener(new TabOnClickListener(3));

        fragments.add(new OfflineManagerFragment());
        fragments.add(new OfflineManagerFragment());
        fragments.add(new OfflineManagerFragment());
        fragments.add(new OfflineManagerFragment());

        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);
        tab4 = (LinearLayout) findViewById(R.id.tab4);

        tab1.setOnClickListener(new TabOnClickListener(0));
        tab2.setOnClickListener(new TabOnClickListener(1));
        tab3.setOnClickListener(new TabOnClickListener(2));
        tab4.setOnClickListener(new TabOnClickListener(3));
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        tab1_text.setTextColor(res.getColor(R.color.text_color));
        tab2_text.setTextColor(res.getColor(R.color.text_color));
        tab3_text.setTextColor(res.getColor(R.color.text_color));
        tab4_text.setTextColor(res.getColor(R.color.text_color));
    }

    /**
     * 功能：点击主页TAB事件
     */
    public class TabOnClickListener implements View.OnClickListener{
        private int index = 0;

        public TabOnClickListener(int i){
            index = i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);//选择某一页
        }

    }

    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            layoutParams.leftMargin= (int) ((positionOffset + position) * screenWidth / tabNumber);
            mTabLine.setLayoutParams(layoutParams);
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            //重置所有TextView的字体颜色
            resetTextView();
            switch (position) {
                case 0:
                    tab1_text.setTextColor(res.getColor(R.color.tab_text_selected));
                    break;
                case 1:
                    tab2_text.setTextColor(res.getColor(R.color.tab_text_selected));
                    break;
                case 2:
                    tab3_text.setTextColor(res.getColor(R.color.tab_text_selected));
                    break;
                case 3:
                    tab4_text.setTextColor(res.getColor(R.color.tab_text_selected));
                    break;
            }
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        public Fragment getItem(int fragment) {
            return fragments.get(fragment);
        }

        public int getCount() {
            return fragments.size();
        }

    }

}



