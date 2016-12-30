package com.cetcme.shipmonitor;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Describe:</br>
 * 可扩展的ListView</br>
 * 本实例主要通过扩展BaseExpandableListAdapter来实现ExpandableListAdapter</br>
 * 并通过ExpandableListAdapter为ExpandableListView设置数据适配器</br>
 * 另外，本实例为ExpandableListView的子列表单击事件设置监听器*
 * @author jph
 * Date:2014.07.14
 * */

public class ExpandableListViewActivity extends Activity {

    private String TAG = "ExpandableListViewActivity";

    private ExpandableListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        list = (ExpandableListView) findViewById(R.id.list);

        //创建一个BaseExpandableListAdapter对象
        final ExpandableListAdapter adapter = new ExpandableListAdapter();

        list.setAdapter(adapter);
        //为ExpandableListView的子列表单击事件设置监听器
        list.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(ExpandableListViewActivity.this, "你单击了："
                        +adapter.getChild(groupPosition, childPosition), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.i(TAG, "onGroupClick: " + groupPosition);
                return false;
            }
        });
    }

    class ExpandableListAdapter extends BaseExpandableListAdapter {

        //设置组视图的图片
        int[] logos = new int[] { R.drawable.tab1, R.drawable.tab2,R.drawable.tab3};

        //设置组视图的显示文字
        private String[] category = new String[] { "僵尸", "魔法植物", "远程植物" };

        //子视图显示文字
        private String[][] subCategory = new String[][] {
                { "旗帜僵尸", "铠甲僵尸", "书生见识" },
                { "黄金蘑菇", "贪睡蘑菇", "大头蘑菇" },
                { "满天星",  "风车植物", "带刺植物" }
        };

        //子视图图片
        public int[][] subLogos = new int[][] {
                { R.drawable.tab1, R.drawable.tab1, R.drawable.tab1},
                { R.drawable.tab1, R.drawable.tab1, R.drawable.tab1},
                { R.drawable.tab1, R.drawable.tab1, R.drawable.tab1}
        };

        //定义一个显示文字信息的方法
        TextView getTextView(){
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
            TextView textView = new TextView(ExpandableListViewActivity.this);
            //设置 textView控件的布局
            textView.setLayoutParams(lp);
            //设置该textView中的内容相对于textView的位置
            textView.setGravity(Gravity.CENTER_VERTICAL);
            //设置txtView的内边距
            textView.setPadding(36, 0, 0, 0);
            //设置文本颜色
            textView.setTextColor(Color.BLACK);
            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
//            // TODO Auto-generated method stub
//            //定义一个LinearLayout用于存放ImageView、TextView
//            LinearLayout view = new LinearLayout(ExpandableListViewActivity.this);
//            //设置子控件的显示方式为水平
//            view.setOrientation(LinearLayout.HORIZONTAL);
//            view.setPadding(20, 20, 20, 20);
//            //定义一个ImageView用于显示列表图片
//            ImageView logo = new ImageView(ExpandableListViewActivity.this);
//            logo.setPadding(50, 0, 0, 0);
//            //设置logo的大小(50（padding）+46=96)
//            AbsListView.LayoutParams lparParams = new AbsListView.LayoutParams(96, 50);
//            logo.setLayoutParams(lparParams);
//            logo.setImageResource(logos[groupPosition]);
//            view.addView(logo);
//            TextView textView = getTextView();
//            textView.setTextSize(15);
//            textView.setText(category[groupPosition]);
//            view.addView(textView);

            //定义一个LinearLayout用于存放ImageView、TextView
            LinearLayout view = new LinearLayout(ExpandableListViewActivity.this);
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            view.setLayoutParams(lp);

            //设置子控件的显示方式为水平
            view.setOrientation(LinearLayout.VERTICAL);
            view.setPadding(60, 0, 0, 0);

            View innerView = LayoutInflater.from(ExpandableListViewActivity.this).inflate(R.layout.info_list_cell, null);
            TextView text = (TextView) innerView.findViewById(R.id.textView_in_List_Cell);
            text.setText(category[groupPosition]);

            ImageView imageView = (ImageView) innerView.findViewById(R.id.imageView_in_List_Cell);
            imageView.setVisibility(View.INVISIBLE);

            view.addView(innerView);


            return view;
        }

        //取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        //取得分组数
        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return category.length;
        }

        //取得与给定分组关联的数据
        @Override
        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
            return category[groupPosition];
        }

        //取得指定分组的子元素数.
        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            return subCategory[groupPosition].length;
        }

        //取得显示给定分组给定子位置的数据用的视图
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
//            //定义一个LinearLayout用于存放ImageView、TextView
//            LinearLayout ll = new LinearLayout(ExpandableListViewActivity.this);
//            //设置子控件的显示方式为水平
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            //定义一个ImageView用于显示列表图片
//            ImageView logo = new ImageView(ExpandableListViewActivity.this);
//            logo.setPadding(0, 0, 0, 0);
//            //设置logo的大小
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40, 40);
//            logo.setLayoutParams(lp);
//            logo.setImageResource(subLogos[groupPosition][childPosition]);
//            ll.addView(logo);
//            TextView textView = getTextView();
//            textView.setText(subCategory[groupPosition][childPosition]);
//            ll.addView(textView);

            //定义一个LinearLayout用于存放ImageView、TextView
            LinearLayout view = new LinearLayout(ExpandableListViewActivity.this);

            //设置子控件的显示方式为水平
            view.setOrientation(LinearLayout.VERTICAL);
            view.setPadding(100, 0, 0, 0);

            View innerView = LayoutInflater.from(ExpandableListViewActivity.this).inflate(R.layout.info_list_cell, null);
            TextView text = (TextView) innerView.findViewById(R.id.textView_in_List_Cell);
            text.setText(subCategory[groupPosition][childPosition]);
            text.setTextSize(13);

            ImageView imageView = (ImageView) innerView.findViewById(R.id.imageView_in_List_Cell);
            imageView.setVisibility(View.INVISIBLE);

            view.addView(innerView);
            view.setBackgroundColor(Color.rgb(219, 219, 219));

            return view;
        }
        //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return subCategory[groupPosition][childPosition];
        }
    }


}