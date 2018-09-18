package com.hcpt.multileagues.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.TabsMainAdapter;
import com.hcpt.multileagues.objects.LeagueObj;

import java.util.ArrayList;


public class FragmentTabLeague extends Fragment {
    private View view;
    private TabLayout tabs;
    private ViewPager pager;
    private TextView border_bottom_tab;
    private TabsMainAdapter tabsMainAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_tableague, container, false);
        initUI();
        initSingleLeague();
        return view;
    }

    private void initUI() {
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);
        border_bottom_tab = (TextView) view.findViewById(R.id.border_bottom_tab);
        tabs.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    private void initSingleLeague() {
        // Show tabs
        tabsMainAdapter = new TabsMainAdapter(getActivity().getSupportFragmentManager(), getActivity());
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(tabsMainAdapter);
    }
}
