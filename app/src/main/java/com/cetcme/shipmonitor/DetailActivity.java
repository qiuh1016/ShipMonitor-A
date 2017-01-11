package com.cetcme.shipmonitor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.qiuhong.qhlibrary.QHTitleView.QHTitleView;
import com.qiuhong.qhlibrary.Utils.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    private String TAG = "DetailActivity";

    private String title;

    private TextView pointImage1;
    private TextView pointImage2;
    private TextView pointImage3;
    private TextView pointImage4;

    private TextView pointImage5;
    private TextView pointImage6;

    private TextView detailText_1;
    private TextView detailText_2;
    private TextView detailText_3;
    private TextView detailText_4;
    private TextView valueText_1;
    private TextView valueText_2;
    private TextView valueText_3;
    private TextView valueText_4;

    private TextView valueText_5;
    private TextView valueText_6;

    private int dangweiValue;

    int lastMainSpeed = 0;
    int lastWeiSpeed = 0;
    int lastOilPercent = 0;
    int lastShedingdang = 0;
    int lastZhixingdang = 0;

//    int currentMainSpeed = 1500;
//    int currentWeiSpeed = -750;
//    int currentOilPercent = 60;
//    int currentShedingdang = 30;
//    int currentZhixingdang = -20;

    int currentMainSpeed = 0;
    int currentWeiSpeed = 0;
    int currentOilPercent = 0;
    int currentShedingdang = 0;
    int currentZhixingdang = 0;

    private Thread getDataThread;
    private boolean toGetData = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        title = getIntent().getExtras().getString("title");

        initNavigationView();
        initView();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                currentMainSpeed = 300;
//                currentWeiSpeed = 200;
//                currentOilPercent = 40;
//
//                currentShedingdang = 20;
//                currentZhixingdang = 30;
//
//                updateValue();
//            }
//        }, 5000);


        final int sleepTime = 5;
        getDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (toGetData) {
                    Log.i(TAG, "run: to get data");
                    getData();

                    //设置每次时间间隔；
                    try {
                        Thread.sleep(sleepTime * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getDataThread.start();
    }

    public void onBackPressed() {
        toGetData = false;
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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, frameWidth * 60 / 100);
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

        //设置档位layout尺寸
        int layoutWidth = screenWidth - margin;
        LinearLayout.LayoutParams dangweiLP = new LinearLayout.LayoutParams(layoutWidth,  layoutWidth * 555 / 1620 );
        dangweiLP.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
        findViewById(R.id.dangwei).setLayoutParams(dangweiLP);


        //档位数值的尺寸
        int dangweiMargin = DensityUtil.dip2px(this, 8);
        int dangweiValueLayoutWidth = layoutWidth- DensityUtil.dip2px(this, 90);
        LinearLayout.LayoutParams dangweiValueLP = new LinearLayout.LayoutParams(dangweiValueLayoutWidth, dangweiValueLayoutWidth * 15 / 524);
        dangweiValueLP.setMargins(DensityUtil.dip2px(this, 50), dangweiMargin, DensityUtil.dip2px(this, 40), dangweiMargin);
        findViewById(R.id.dangwei_value_layout).setLayoutParams(dangweiValueLP);

        //
        dangweiValue = dangweiValueLayoutWidth;

        initTextView();
        initPointer();
        initValue();


    }

    private void initPointer() {
        pointImage1 = (TextView) findViewById(R.id.imageView_point);
        pointImage2 = (TextView) findViewById(R.id.imageView_point_2);
        pointImage3 = (TextView) findViewById(R.id.imageView_point_3);
        pointImage4 = (TextView) findViewById(R.id.imageView_point_4);
        pointImage5 = (TextView) findViewById(R.id.imageView_point_5);
        pointImage6 = (TextView) findViewById(R.id.imageView_point_6);
    }

    private void initTextView() {
        detailText_1 = (TextView) findViewById(R.id.detail_text_1);
        detailText_2 = (TextView) findViewById(R.id.detail_text_2);
        detailText_3 = (TextView) findViewById(R.id.detail_text_3);
        detailText_4 = (TextView) findViewById(R.id.detail_text_4);

        valueText_1 = (TextView) findViewById(R.id.value_text_1);
        valueText_2 = (TextView) findViewById(R.id.value_text_2);
        valueText_3 = (TextView) findViewById(R.id.value_text_3);
        valueText_4 = (TextView) findViewById(R.id.value_text_4);

        valueText_5 = (TextView) findViewById(R.id.value_text_5);
        valueText_6 = (TextView) findViewById(R.id.value_text_6);

    }

    private void initValue() {
        moveImage(pointImage5, 0.01f, dangweiValue / 2 - 2);
        moveImage(pointImage6, 0.01f, dangweiValue / 2 - 2);

        ObjectAnimator anim = ObjectAnimator.ofFloat(pointImage2, "rotation", 0f, 90f);
        anim.setDuration(10);
        anim.start();
    }

    private void updateValue() {
        rotateImage(pointImage1, lastMainSpeed / 1500f * 180f           , currentMainSpeed / 1500f * 180f);
        rotateImage(pointImage2, (lastWeiSpeed + 1500) / 3000f * 180f   , (currentWeiSpeed + 1500) / 3000f * 180f);
        rotateImage(pointImage3, lastOilPercent / 100f * 180f          , currentOilPercent / 100f * 180f);
        valueText_1.setText( currentMainSpeed + " RPM");
        valueText_2.setText(currentWeiSpeed + " RPM");
        valueText_3.setText(currentOilPercent + " %");

        int moveDistace = dangweiValue / 2 - 2;

//        moveImage(pointImage5, Math.abs(currentShedingdang - lastShedingdang) / 50f * 5f, currentShedingdang / 50f * moveDistace + moveDistace);
//        moveImage(pointImage6, Math.abs(currentZhixingdang - lastZhixingdang) / 50f * 5f, currentZhixingdang / 50f * moveDistace + moveDistace);
        moveImage(pointImage5, 2f, currentShedingdang / 50f * moveDistace + moveDistace);
        moveImage(pointImage6, 2f, currentZhixingdang / 100f * moveDistace + moveDistace);

        valueText_5.setText((currentShedingdang - lastShedingdang) * 2 + "%");
        valueText_6.setText((currentZhixingdang - lastZhixingdang) * 2 + "%");
        valueText_5.setText(currentShedingdang * 2 + "%");
        valueText_6.setText(currentZhixingdang + "%");

        lastMainSpeed = currentMainSpeed;
        lastWeiSpeed = currentWeiSpeed;
        lastOilPercent = currentOilPercent;
        lastShedingdang = currentShedingdang;
        lastZhixingdang = currentZhixingdang;
    }

    private void rotateImage(View v, float from, float to) {
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", from, to);

//        int t = (int) (Math.abs(to - from) / 180 * 1000 * 2);
        int t = 2 * 1000;
        anim.setDuration(t);

        // 回调监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
            }
        });

        anim.start();
    }

    private void moveImage(View v, float time, float x) {
        ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(v, "translationX", x);
        valueAnimator.setDuration((int)time * 1000);
        valueAnimator.start();
    }

    private void getData() {
        String url = getString(R.string.test_url);

        SyncHttpClient client = new SyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("post", "onSuccess: " + response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseJSON(response);
                    }
                });

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }

        });
    }

    private void parseJSON(JSONObject response) {

        String meCode = "";
        String code900 = "";
        String code901 = "";
        String code902 = "";
        String code903 = "";
        String code904 = "";
        String code905 = "";
        String code906 = "";
        String code907 = "";
        String code908 = "";
        String code909 = "";
        String code910 = "";

        try {
            meCode = response.getString("meCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code900 = response.getString("900");
            currentShedingdang = Integer.parseInt(code900);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code901 = response.getString("901");
            //detailText_1.setText("辅车钟状态" + "\n");
            detailText_1.setText("");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code902 = response.getString("902");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code903 = response.getString("903");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code904 = response.getString("904");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code905 = response.getString("905");
            //detailText_3.setText("控制位置" + "\n");
            detailText_3.setText("");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code906 = response.getString("906");
            //detailText_4.setText("控制模式" + "\n");
            detailText_4.setText("");
            switch (code906) {
                case "0":
                    detailText_4.append("闭环模式");
                    break;
                case "1":
                    detailText_4.append("开环模式");
                    break;
                default:
                    detailText_4.append("有误");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code907 = response.getString("907");
            //detailText_2.setText("齿轮箱状态" + "\n");
            detailText_2.setText("");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code908 = response.getString("908");
            currentMainSpeed = Integer.parseInt(code908);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code909 = response.getString("909");
            currentWeiSpeed = Integer.parseInt(code909);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            code910 = response.getString("910");
            currentOilPercent = Integer.parseInt(code910);

            switch (code907) {
                case "0":                                  // 正车
                    currentZhixingdang = Integer.parseInt(code910);
                    break;
                case "1":                                  // 倒车
                    currentZhixingdang = 0 - Integer.parseInt(code910);
                    break;
                case "2":                                  // 空车
                    currentZhixingdang = 0;
                    break;
                default:                                   // 有误
                    currentZhixingdang = 0;
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        updateValue();

//            String meCode = response.getString("meCode");
//            String code900 = response.getString("900");
//            String code901 = response.getString("901");
//            String code902 = response.getString("902");
//            String code903 = response.getString("903");
//            String code904 = response.getString("904");
//            String code905 = response.getString("905");
//            String code906 = response.getString("906");
//            String code907 = response.getString("907");
//            String code908 = response.getString("908");
//            String code909 = response.getString("909");
//            String code910 = response.getString("910");

//            mTextView.append("主机遥控编号：" + meCode + "\n");
//            mTextView.append("当前车钟：" + code900 + "\n");



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



//            valueText_1.setText(code908 + " RPM\n");
//            valueText_2.setText(code909 + " RPM\n");
//            valueText_3.setText(code910 + " %\n");


//
//            if (currentShedingdang < 0) {
//                currentZhixingdang = 0 - Integer.parseInt(code910);
//            } else if (currentShedingdang > 0) {
//                currentZhixingdang = Integer.parseInt(code910);
//            } else {
//                currentZhixingdang = Integer.parseInt(code910);
//            }

//            currentZhixingdang = Integer.parseInt(code900);



    }

}
