package com.hcpt.multileagues.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcpt.multileagues.fragments.EarnerFragment;
import com.hcpt.multileagues.fragments.FragmentDetailsTeam;
import com.hcpt.multileagues.fragments.FragmentLineUpTeam;
import com.hcpt.multileagues.fragments.SpentPointFragment;

public class DetailsTeamAdapter extends FragmentPagerAdapter {

    public DetailsTeamAdapter(FragmentManager fm) {
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
                fragment = new FragmentDetailsTeam();
                break;
            case 1:
                fragment = new FragmentLineUpTeam();
                break;

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "DETAILS";
                break;
            case 1:
                title = "LINE UP";
                break;
        }
        return title;
    }
}