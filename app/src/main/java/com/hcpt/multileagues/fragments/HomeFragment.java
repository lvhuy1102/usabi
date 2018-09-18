package com.hcpt.multileagues.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.HomeMainAdapter;

public class HomeFragment extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeMainAdapter adapter;
    public static HomeFragment homeFragment;

    public static HomeFragment newInstance() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        com.hcpt.multileagues.utilities.Debug.e("CReATVIEW");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.tvTitle);
        textView.setText("HOME");
        FragmentManager manager = getChildFragmentManager();
        adapter = new HomeMainAdapter(manager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.hcpt.multileagues.utilities.Debug.e("CREAT");
    }

}
