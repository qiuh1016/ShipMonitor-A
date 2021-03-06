package com.cetcme.shipmonitor.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.shipmonitor.DetailActivity;
import com.cetcme.shipmonitor.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ShipsFragment extends Fragment {

    private String TAG = "ShipsFragment";

    private ExpandableListView list;
    private ShipsFragment.ExpandableListAdapter adapter;

    //设置组视图的图片
    int[] logos = new int[] { R.drawable.tab1, R.drawable.tab2,R.drawable.tab3};

    //设置组视图的显示文字
    private String[] category = new String[]{};
//    { "长风77", "建工86", "xxx" };

    //子视图显示文字
    private String[][] subCategory = new String[][]{};
//
//            {
//            { "左机：宁动6700", "右机：宁动6700" },
//            { "左机：宁动6700", "右机：宁动6700" },
//            { "左机：宁动6700", "右机：宁动6700" }
//    };

    private String[][] meCodes = new String[][]{};

    //子视图图片
    public int[][] subLogos = new int[][] {
            { R.drawable.tab1, R.drawable.tab1, R.drawable.tab1},
            { R.drawable.tab1, R.drawable.tab1, R.drawable.tab1},
            { R.drawable.tab1, R.drawable.tab1, R.drawable.tab1}
    };

    public static ShipsFragment newInstance(String param1) {
        ShipsFragment fragment = new ShipsFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ShipsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ships, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        list = (ExpandableListView) view.findViewById(R.id.list);

        //创建一个BaseExpandableListAdapter对象
        adapter = new ShipsFragment.ExpandableListAdapter();

        list.setAdapter(adapter);
        //为ExpandableListView的子列表单击事件设置监听器
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(getActivity(), "你单击了：" + adapter.getChild(groupPosition, childPosition), Toast.LENGTH_LONG).show();

                //打开详细界面
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle titleBundle = new Bundle();
                titleBundle.putString("title", category[groupPosition] + " " + subCategory[groupPosition][childPosition]);
                titleBundle.putString("meCode", meCodes[groupPosition][childPosition]);
                intent.putExtras(titleBundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                return true;
            }
        });

        getListData();

        return view;
    }

    private void getListData() {
        String url = getString(R.string.server_ip) + getString(R.string.list_url);

        RequestParams params = new RequestParams();
        params.put("shipNo", "hdyTest");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray



                Log.i("post", "onSuccess: " + response);
                try {
//                    JSONArray array = null;
//                    response.toJSONArray(array);
                    JSONObject meCode_0 = response.getJSONObject(0);
                    JSONObject meCode_1 = response.getJSONObject(1);
//                    subCategory[0][0] = meCode_0.getString("controlName") + ": " + meCode_0.getString("meCode");
//                    subCategory[0][1] = meCode_1.getString("controlName") + ": " + meCode_1.getString("meCode");
//                    category[0]       = meCode_0.getString("shipName");

                    category = new String[] { meCode_0.getString("shipName")};

                    subCategory = new String[][] {
                            { meCode_0.getString("controlName") + "：" + meCode_0.getString("meCode"), meCode_1.getString("controlName") + "：" + meCode_1.getString("meCode")},
                    };

                    meCodes = new String[][] {
                            { meCode_0.getString("meCode"), meCode_1.getString("meCode")},
                    };

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getActivity(), "获取列表错误", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class ExpandableListAdapter extends BaseExpandableListAdapter {

        //定义一个显示文字信息的方法
        TextView getTextView(){
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
            TextView textView = new TextView(getActivity());
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
//            LinearLayout view = new LinearLayout(getActivity());
//            //设置子控件的显示方式为水平
//            view.setOrientation(LinearLayout.HORIZONTAL);
//            view.setPadding(20, 20, 20, 20);
//            //定义一个ImageView用于显示列表图片
//            ImageView logo = new ImageView(getActivity());
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
            LinearLayout view = new LinearLayout(getActivity());
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            view.setLayoutParams(lp);

            //设置子控件的显示方式为水平
            view.setOrientation(LinearLayout.VERTICAL);
            view.setPadding(60, 0, 0, 0);

            View innerView = LayoutInflater.from(getActivity()).inflate(R.layout.info_list_cell, null);
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
//            LinearLayout ll = new LinearLayout(getActivity());
//            //设置子控件的显示方式为水平
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            //定义一个ImageView用于显示列表图片
//            ImageView logo = new ImageView(getActivity());
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
            LinearLayout view = new LinearLayout(getActivity());

            //设置子控件的显示方式为水平
            view.setOrientation(LinearLayout.VERTICAL);
            view.setPadding(100, 0, 0, 0);

            View innerView = LayoutInflater.from(getActivity()).inflate(R.layout.info_list_cell, null);
            TextView text = (TextView) innerView.findViewById(R.id.textView_in_List_Cell);
            text.setText(subCategory[groupPosition][childPosition]);
            text.setTextSize(13);

            ImageView imageView = (ImageView) innerView.findViewById(R.id.imageView_in_List_Cell);
            imageView.setVisibility(View.INVISIBLE);

            view.addView(innerView);
            int backColor = 230;
//            view.setBackgroundColor(Color.rgb(backColor, backColor, backColor));
            view.setBackgroundColor(getActivity().getResources().getColor(R.color.list_back_color));
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
