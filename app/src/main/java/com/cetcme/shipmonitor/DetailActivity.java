package com.cetcme.shipmonitor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qiuhong.qhlibrary.QHTitleView.QHTitleView;
import com.qiuhong.qhlibrary.Utils.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    private String TAG = "MoreInfoFragment_2";

    private String title;

    private TextView pointImage;
    private TextView pointImage2;
    private TextView pointImage3;

    private TextView detailText_1;
    private TextView detailText_2;
    private TextView detailText_3;
    private TextView detailText_4;
    private TextView valueText_1;
    private TextView valueText_2;
    private TextView valueText_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        title = getIntent().getExtras().getString("title");

        getTestData();
        initNavigationView();
        initView();
        initPointer();
        initTextView();




    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        QHTitleView navigationView = (QHTitleView) findViewById(R.id.nav_main_in_detail_activity);
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

    private void initView() {

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int margin = DensityUtil.dip2px(this, 30);
        int frameWidth = (screenWidth - 3 * margin) / 2;

        //改变两个横向layout的宽高
        LinearLayout zhuanpanLayout = (LinearLayout) findViewById(R.id.zhuanpan_layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, frameWidth * 6 / 10);
        params.setMargins(0, margin / 5, 0, 0);
        zhuanpanLayout.setLayoutParams(params);

        findViewById(R.id.zhuanpan_layout_2).setLayoutParams(params);

        //设置frame 宽等于高
        FrameLayout frame_1 = (FrameLayout) findViewById(R.id.frame_1);
        FrameLayout frame_2 = (FrameLayout) findViewById(R.id.frame_2);
        FrameLayout frame_3 = (FrameLayout) findViewById(R.id.frame_3);
        FrameLayout frame_4 = (FrameLayout) findViewById(R.id.frame_4);
        LinearLayout.LayoutParams newFrameLp = new LinearLayout.LayoutParams(frameWidth , frameWidth);
        newFrameLp.setMargins(margin, 0, 0 ,0);
        frame_1.setLayoutParams(newFrameLp);
        frame_2.setLayoutParams(newFrameLp);
        frame_3.setLayoutParams(newFrameLp);
        frame_4.setLayoutParams(newFrameLp);

        //隐藏第四个转盘
        findViewById(R.id.frame_4).setVisibility(View.INVISIBLE);

        //设置执行档位layout尺寸
        int layoutWidth = screenWidth - 2 * margin;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(layoutWidth,  layoutWidth * 555 / 1620 );
        params1.setMargins(margin, margin, margin, margin);
        findViewById(R.id.zhidingdang).setLayoutParams(params1);
    }

    private void initPointer() {
        pointImage = (TextView) findViewById(R.id.imageView_point);
        pointImage2 = (TextView) findViewById(R.id.imageView_point_2);
        pointImage3 = (TextView) findViewById(R.id.imageView_point_3);
        rotateImage(pointImage, 0f, 180f);
        rotateImage(pointImage2, 180f, 0f);
    }

    private void initTextView() {
        detailText_1 = (TextView) findViewById(R.id.detail_text_1);
        detailText_2 = (TextView) findViewById(R.id.detail_text_2);
        detailText_3 = (TextView) findViewById(R.id.detail_text_3);
        detailText_4 = (TextView) findViewById(R.id.detail_text_4);

        valueText_1 = (TextView) findViewById(R.id.value_text_1);
        valueText_2 = (TextView) findViewById(R.id.value_text_2);
        valueText_3 = (TextView) findViewById(R.id.value_text_3);

    }


    private void rotateImage(View v, float from, float to) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", from, to);

        // 动画的持续时间，执行多久？
        anim.setDuration(10000);

        // 回调监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
            }
        });

        // 正式开始启动执行动画
        anim.start();
    }

    private void getTestData() {
        String url = getString(R.string.test_url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("post", "onSuccess: " + response);
                parseJSON(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }

        });
    }


    private void parseJSON(JSONObject response) {
        try {
            String meCode = response.getString("meCode");
            String code900 = response.getString("900");
            String code901 = response.getString("901");
            String code902 = response.getString("902");
            String code903 = response.getString("903");
            String code904 = response.getString("904");
            String code905 = response.getString("905");
            String code906 = response.getString("906");
            String code907 = response.getString("907");
            String code908 = response.getString("908");
            String code909 = response.getString("909");
            String code910 = response.getString("910");

//            mTextView.append("主机遥控编号：" + meCode + "\n");
//            mTextView.append("当前车钟：" + code900 + "\n");

            detailText_1.setText("辅车钟状态" + "\n");
            switch (code901) {
                case "0":
                    detailText_1.append("空");
                    break;
                case "1":
                    detailText_1.append("海上");
                    break;
                case "2":
                    detailText_1.append("备车");
                    break;
                case "3":
                    detailText_1.append("完车");
                    break;
                default:
                    detailText_1.append("有误");
                    break;
            }

//            switch (code902) {
//                case "0":
//                    mTextView.append("越控自动降速：" + "未启用" + "\n");
//                    break;
//                case "1":
//                    mTextView.append("越控自动降速：" + "启用" + "\n");
//                    break;
//                default:
//                    mTextView.append("越控自动降速：" + "有误" + "\n");
//                    break;
//            }

//            switch (code903) {
//                case "0":
//                    mTextView.append("越控自动停车：" + "未启用" + "\n");
//                    break;
//                case "1":
//                    mTextView.append("越控自动停车：" + "启用" + "\n");
//                    break;
//                default:
//                    mTextView.append("越控自动停车：" + "有误" + "\n");
//                    break;
//            }

//            mTextView.append("主机设定转速：" + code904 + "RPM\n");


            detailText_3.setText("控制位置" + "\n");
            switch (code905) {
                case "0":
                    detailText_3.append("机旁");
                    break;
                case "1":
                    detailText_3.append("驾控室");
                    break;
                case "2":
                    detailText_3.append("集控室");
                    break;
                default:
                    detailText_3.append("有误");
                    break;
            }

            detailText_4.setText("控制模式" + "\n");
            switch (code906) {
                case "0":
                    detailText_4.append("闭环模式");
                    break;
                case "1":
                    detailText_3.append("开环模式");
                    break;
                default:
                    detailText_3.append("有误");
                    break;
            }

            detailText_2.setText("齿轮箱状态" + "\n");
            switch (code907) {
                case "0":
                    detailText_2.append("正车");
                    break;
                case "1":
                    detailText_2.append("倒车");
                    break;
                case "2":
                    detailText_2.append("空车");
                    break;
                default:
                    detailText_2.append("有误");
                    break;
            }

            valueText_1.setText(code908 + " RPM\n");
            valueText_2.setText(code909 + " RPM\n");
            valueText_3.setText(code910 + " %\n");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
