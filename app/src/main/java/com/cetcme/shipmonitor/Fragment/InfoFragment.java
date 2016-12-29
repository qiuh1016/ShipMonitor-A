package com.cetcme.shipmonitor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cetcme.shipmonitor.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoFragment extends Fragment {

    private static final String TAG = "InfoFragment";
    private ListView mListView;
    private SimpleAdapter mSimpleAdapter;
    private List<Map<String, Object>> dataList;


    public static InfoFragment newInstance(String param1) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        mListView = (ListView) view.findViewById(R.id.info_list);
        mSimpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.info_list_cell, new String[]{"title"}, new int[]{R.id.textView_in_List_Cell});
        mListView.setAdapter(mSimpleAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + position);
            }
        });

        return view;
    }

    private List<Map<String, Object>> getData() {

        dataList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("title", "BASIC INFO");
        dataList.add(map);

        map = new HashMap<>();
        map.put("title", "SIZE");
        dataList.add(map);

        map = new HashMap<>();
        map.put("title", "VOLUME");
        dataList.add(map);

        map = new HashMap<>();
        map.put("title", "MANAGE INFO");
        dataList.add(map);

        return dataList;

    }

}
