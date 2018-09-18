package com.hcpt.multileagues.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.HomeMainAdapter;
import com.hcpt.multileagues.adapters.MyContestAdapter;

public class MyContestFragment extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyContestAdapter myContestAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.mycontest_fragment, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.tvTitle);
        textView.setText("MY CONTEST");
        FragmentManager manager = getChildFragmentManager();
        myContestAdapter = new MyContestAdapter(manager);
        viewPager.setAdapter(myContestAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return view;
    }
}
