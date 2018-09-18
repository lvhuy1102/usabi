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
import com.hcpt.multileagues.adapters.AdapterSpentPoints;

import java.util.ArrayList;

public class SpentPointFragment extends Fragment {
    private View view;
    private RecyclerView rclView;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private AdapterSpentPoints adapterEarnerPoints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_spentpoint, container, false);
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
        adapterEarnerPoints = new AdapterSpentPoints(getActivity(), stringArrayList);
        rclView.setAdapter(adapterEarnerPoints);
        return view;
    }
}
