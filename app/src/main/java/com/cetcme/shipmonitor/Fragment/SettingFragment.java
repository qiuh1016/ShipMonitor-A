package com.cetcme.shipmonitor.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cetcme.shipmonitor.LoginActivity;
import com.cetcme.shipmonitor.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class SettingFragment extends Fragment {

    public static SettingFragment newInstance(String param1) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        Button button = (Button) view.findViewById(R.id.apiTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ts = (int)(System.currentTimeMillis()/1000);
                Log.i("unix", "onClick: " + ts);

                //设置输入参数
                RequestParams params = new RequestParams();
                params.put("meCode", 2015001);
                params.put("dateTime", ts);

                String url = getString(R.string.server_ip)+ getString(R.string.real_time_info);

                AsyncHttpClient client = new AsyncHttpClient();
                client.post(url, params, new JsonHttpResponseHandler("UTF-8"){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        Log.i("post", "onSuccess: " + response);

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                    }

                });
            }
        });

        view.findViewById(R.id.loginTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

        return view;
    }

}
