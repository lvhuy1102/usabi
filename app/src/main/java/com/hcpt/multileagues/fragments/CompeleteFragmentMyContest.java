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
import com.hcpt.multileagues.adapters.AdapterCurrentMyContest;

import java.util.ArrayList;

public class CompeleteFragmentMyContest extends Fragment {
    private View view;
    private RecyclerView rclMatchs;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private AdapterCurrentMyContest adapterCurrentMyContest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_lobby, container, false);
        rclMatchs = view.findViewById(R.id.rclMatchs);
        stringArrayList.add("REAL MARID");
        stringArrayList.add("REAL MARID");
        stringArrayList.add("REAL MARID");
        stringArrayList.add("REAL MARID");
        stringArrayList.add("REAL MARID");
        stringArrayList.add("REAL MARID");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclMatchs.setLayoutManager(layoutManager);
        adapterCurrentMyContest = new AdapterCurrentMyContest(getActivity(), stringArrayList);
        rclMatchs.setAdapter(adapterCurrentMyContest);
        return view;
    }
}
