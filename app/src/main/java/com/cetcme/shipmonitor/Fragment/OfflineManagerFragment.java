package com.cetcme.shipmonitor.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.shipmonitor.R;


public class OfflineManagerFragment extends Fragment {


    private View view;
    private Context context;


    private Toast deleteToast;

    private String TAG = "OfflineManagerFragment";


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offline_manager, container, false);
        context = this.getActivity();

        showNoDataLayout(true);
        return view;
    }



    private void showNoDataLayout(boolean show) {
        LinearLayout noDataLayout = (LinearLayout) view.findViewById(R.id.no_data_layout);
        LinearLayout localMapLayout = (LinearLayout) view.findViewById(R.id.localmap_layout);
        noDataLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        localMapLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }



}