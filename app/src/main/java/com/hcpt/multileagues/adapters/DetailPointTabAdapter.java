package com.hcpt.multileagues.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcpt.multileagues.fragments.CompeleteFragment;
import com.hcpt.multileagues.fragments.EarnerFragment;
import com.hcpt.multileagues.fragments.LobbyFragment;
import com.hcpt.multileagues.fragments.SpentPointFragment;
import com.hcpt.multileagues.fragments.UpcommingFragment;

public class DetailPointTabAdapter extends FragmentPagerAdapter {

    public DetailPointTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new EarnerFragment();
                break;
            case 1:
                fragment = new SpentPointFragment();
                break;

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "EARNER POINTS";
                break;
            case 1:
                title = "SPENT POINTS";
                break;
        }
        return title;
    }
}