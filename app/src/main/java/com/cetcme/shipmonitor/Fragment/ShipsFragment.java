package com.cetcme.shipmonitor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cetcme.shipmonitor.R;


public class ShipsFragment extends Fragment {

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

        return view;
    }

}
