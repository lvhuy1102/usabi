package com.hcpt.multileagues.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.AdapterLeaderBoard;
import com.hcpt.multileagues.adapters.RankClubAdapterNew;
import com.hcpt.multileagues.configs.GlobalValue;

import java.util.ArrayList;

public class LeaderboardFragment extends Fragment {
    private RecyclerView rclView;
    private AdapterLeaderBoard adapterLeaderBoard;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.leaderbroard_fragment, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.tvTitle);
        textView.setText("LEADERBROARD");
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclView.setLayoutManager(layoutManager);
        adapterLeaderBoard = new AdapterLeaderBoard(stringArrayList, getActivity());
        rclView.setAdapter(adapterLeaderBoard);
        return view;
    }
}
