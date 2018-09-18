package com.hcpt.multileagues.adapters;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.configs.Constants;
import com.hcpt.multileagues.configs.JsonConfigs;
import com.hcpt.multileagues.fragments.ChatRoomFragment;
import com.hcpt.multileagues.fragments.LineupsFragment;
//import com.hcpt.multileagues.fragments.TimeLineFragment;
import com.hcpt.multileagues.fragments.StatsFragment;
import com.hcpt.multileagues.fragments.TimelineFragmentNew;

public class TabsMatchDetailAdapter extends FragmentPagerAdapter {

    private int TABS = 2;

    private String[] TITLES;
    private String mMatchId;
    private Activity mAct;
    private String matchStatus;

    public TabsMatchDetailAdapter(FragmentManager fm, Activity act, final String mMatchStatus) {
        super(fm);
        this.mAct = act;
        this.matchStatus = mMatchStatus;

        TABS = 4;
        TITLES = new String[TABS];
        TITLES[0] = act.getResources().getString(R.string.text_lineups);
        TITLES[1] = act.getResources().getString(R.string.text_timeline);
        TITLES[2] = act.getResources().getString(R.string.text_chatroom);
        TITLES[3] = act.getResources().getString(R.string.text_stats);


    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TABS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LineupsFragment();
            case 1:
                return new TimelineFragmentNew();
            case 2:
                return new ChatRoomFragment();
            case 3:
                return new StatsFragment();
            default:
                throw new IllegalArgumentException(
                        "The item position should be less: " + TABS);

        }
        // return SuperAwesomeCardFragment.newInstance(position);
    }

}
