package com.hcpt.multileagues.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcpt.multileagues.fragments.CompeleteFragment;
import com.hcpt.multileagues.fragments.LobbyFragment;
import com.hcpt.multileagues.fragments.UpcommingFragment;

public class HomeMainAdapter extends FragmentPagerAdapter {

    public HomeMainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = LobbyFragment.newInstance();
                break;
            case 1:
                fragment = UpcommingFragment.newInstance();
                break;
            case 2:
                fragment = CompeleteFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "lobby";
                break;
            case 1:
                title = "Upcomming";
                break;
            case 2:
                title = "compelete";
                break;
        }
        return title;
    }
}
