package com.cetcme.shipmonitor.useless;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cetcme.shipmonitor.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MoreInfoFragment_1 extends Fragment {


    private View view;
    private Context context;

    private String TAG = "MoreInfoFragment_1";
    private TextView mTextView;


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more_info_fragment_1, container, false);
        context = this.getActivity();

        mTextView = (TextView) view.findViewById(R.id.data_textView_in_more_info);
        mTextView.setText("");
        getTestData();

        return view;
    }

    private void getTestData() {
        String url = getString(R.string.server_ip) + getString(R.string.detail_info_url);

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

            mTextView.append("主机遥控编号：" + meCode + "\n");
            mTextView.append("当前车钟：" + code900 + "\n");

            switch (code901) {
                case "0":
                    mTextView.append("辅车钟状态：" + "空" + "\n");
                    break;
                case "1":
                    mTextView.append("辅车钟状态：" + "海上" + "\n");
                    break;
                case "2":
                    mTextView.append("辅车钟状态：" + "备车" + "\n");
                    break;
                case "3":
                    mTextView.append("辅车钟状态：" + "完车" + "\n");
                    break;
                default:
                    mTextView.append("辅车钟状态：" + "有误" + "\n");
                    break;
            }

            switch (code902) {
                case "0":
                    mTextView.append("越控自动降速：" + "未启用" + "\n");
                    break;
                case "1":
                    mTextView.append("越控自动降速：" + "启用" + "\n");
                    break;
                default:
                    mTextView.append("越控自动降速：" + "有误" + "\n");
                    break;
            }

            switch (code903) {
                case "0":
                    mTextView.append("越控自动停车：" + "未启用" + "\n");
                    break;
                case "1":
                    mTextView.append("越控自动停车：" + "启用" + "\n");
                    break;
                default:
                    mTextView.append("越控自动停车：" + "有误" + "\n");
                    break;
            }

            mTextView.append("主机设定转速：" + code904 + "RPM\n");

            switch (code905) {
                case "0":
                    mTextView.append("控制位置：" + "机旁" + "\n");
                    break;
                case "1":
                    mTextView.append("控制位置：" + "驾控室" + "\n");
                    break;
                case "2":
                    mTextView.append("控制位置：" + "集控室" + "\n");
                    break;
                default:
                    mTextView.append("控制位置：" + "有误" + "\n");
                    break;
            }

            switch (code906) {
                case "0":
                    mTextView.append("控制模式：" + "闭环模式" + "\n");
                    break;
                case "1":
                    mTextView.append("控制模式：" + "开环模式" + "\n");
                    break;
                default:
                    mTextView.append("控制模式：" + "有误" + "\n");
                    break;
            }

            switch (code907) {
                case "0":
                    mTextView.append("齿轮箱状态：" + "正车" + "\n");
                    break;
                case "1":
                    mTextView.append("齿轮箱状态：" + "倒车" + "\n");
                    break;
                case "2":
                    mTextView.append("齿轮箱状态：" + "空车" + "\n");
                    break;
                default:
                    mTextView.append("齿轮箱状态：" + "有误" + "\n");
                    break;
            }

            mTextView.append("主机当前转速：" + code908 + "RPM\n");
            mTextView.append("艉轴转速：" + code909 + "RPM\n");
            mTextView.append("主机供油量：" + code910 + "%\n");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}