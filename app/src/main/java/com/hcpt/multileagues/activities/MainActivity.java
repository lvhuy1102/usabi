package com.hcpt.multileagues.activities;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.fragments.ContestFragment;
import com.hcpt.multileagues.fragments.HomeFragment;
import com.hcpt.multileagues.fragments.LeaderboardFragment;
import com.hcpt.multileagues.fragments.LeagueFragment;
import com.hcpt.multileagues.fragments.LobbyLeftFragment;
import com.hcpt.multileagues.fragments.MyContestFragment;
import com.hcpt.multileagues.fragments.OtherFragment;
import com.hcpt.multileagues.fragments.SettingLeftFragment;
import com.hcpt.multileagues.fragments.ShareFragment;
import com.hcpt.multileagues.fragments.TournameFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }

        //League Activity Need Delete

        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle(null);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        showFragment(HomeFragment.newInstance());
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        showFragment(HomeFragment.newInstance());
                        break;
                    case R.id.league:
                        showFragment(LeagueFragment.newInstance());
                        break;
                    case R.id.tourname:
                        showFragment(new TournameFragment());
                        break;
                    case R.id.other:
                        showFragment(new OtherFragment());
                        break;
                    case R.id.contest:
                        showFragment(new ContestFragment());
                        break;
                    case R.id.share:
                        showFragment(new ShareFragment());
                        break;
                    case R.id.leaderboard:
                        showFragment(new LeaderboardFragment());
                        break;
                    case R.id.lobby:
                        showFragment(new LobbyLeftFragment());
                        break;
                    case R.id.mycontest:
                        showFragment(new MyContestFragment());
                        break;
                    case R.id.setting:
                        showFragment(new SettingLeftFragment());
                        break;

                }

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                return true;
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
    }
}
