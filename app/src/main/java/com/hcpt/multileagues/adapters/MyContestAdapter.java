package com.hcpt.multileagues.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcpt.multileagues.fragments.CompeleteFragment;
import com.hcpt.multileagues.fragments.CompeleteFragmentMyContest;
import com.hcpt.multileagues.fragments.CurrentFragmentMyContest;
import com.hcpt.multileagues.fragments.LobbyFragment;
import com.hcpt.multileagues.fragments.UpcommingFragment;

public class MyContestAdapter extends FragmentPagerAdapter {
    public MyContestAdapter(FragmentManager fm) {
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
                fragment = new CurrentFragmentMyContest();
                break;
            case 1:
                fragment = new CompeleteFragmentMyContest();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "CURRENT";
                break;
            case 1:
                title = "COMPELETED";
                break;
        }
        return title;
    }
}
