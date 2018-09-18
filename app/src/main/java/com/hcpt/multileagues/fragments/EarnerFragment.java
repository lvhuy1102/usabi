package com.hcpt.multileagues.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.AdapterEarnerPoints;
import com.hcpt.multileagues.adapters.AdapterLeaderBoard;

import java.util.ArrayList;

public class EarnerFragment extends Fragment {
    private View view;
    private RecyclerView rclView;
    private AdapterEarnerPoints adapterEarnerPoints;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_earner, container, false);
        rclView = view.findViewById(R.id.rclView);
        stringArrayList.add("Shey-ei");
        stringArrayList.add("Emanuel Tome");
        stringArrayList.add("Bobby");
        stringArrayList.add("King_dragon");
        stringArrayList.add("Shey-ei");
        stringArrayList.add("Emanuel Tome");
        stringArrayList.add("Bobby");
        stringArrayList.add("King_dragon");
        stringArrayList.add("Shey-ei");
        stringArrayList.add("Emanuel Tome");
        rclView = view.findViewById(R.id.rclView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclView.setLayoutManager(layoutManager);
        adapterEarnerPoints = new AdapterEarnerPoints(getActivity(), stringArrayList);
        rclView.setAdapter(adapterEarnerPoints);
        return view;
    }
}
