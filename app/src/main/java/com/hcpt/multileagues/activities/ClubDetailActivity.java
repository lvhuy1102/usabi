package com.hcpt.multileagues.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.DetailPointTabAdapter;
import com.hcpt.multileagues.adapters.DetailsTeamAdapter;
import com.hcpt.multileagues.image.utils.ImageLoader;
import com.hcpt.multileagues.objects.RankClubsObj;

public class ClubDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView tvTitle;
    private ImageView iv_menu, iv_filter, tv_refresh;
    private DetailsTeamAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_details_activity);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tvTitle = findViewById(R.id.tvTitle);
        iv_menu = findViewById(R.id.iv_menu);
        iv_filter = findViewById(R.id.iv_filter);
        tv_refresh = findViewById(R.id.iv_refresh);
        tv_refresh.setVisibility(View.VISIBLE);
        iv_filter.setVisibility(View.VISIBLE);
        iv_menu.setVisibility(View.VISIBLE);
        tvTitle.setText("TEAM DETAILS");
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FragmentManager manager = getSupportFragmentManager();
        adapter = new DetailsTeamAdapter(manager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
    //    public static RankClubsObj currentClub;
//    private ImageView mLogo;
//    private TextView mLblPlayed, mLblGd, mLblPoint, mLblWin, mLblDraw,
//            mLblLose, mLblEstablished, mLblManager, mLblNickName, mLblStadium;
//    private Button mBtnViewDetail;
//    private String url = "";
//    private ImageLoader mImageLoader;
//
//    @SuppressLint("NewApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_club_detail);
//
//        mImageLoader = new ImageLoader(getBaseContext());
//
//        try {
//            // Set title(Club's name)
//            getSupportActionBar().setTitle(currentClub.getmNameClubs());
//
//            // Show as up button.
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//        }
//
//        initControls();
//        initData();
//
//        // Admob
//        LeagueActivity.initBannerAdsOnActivity(this, R.id.ll_admob);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void initControls() {
////        mLogo = (ImageView) findViewById(R.id.img_club_detail);
//        mLblDraw = (TextView) findViewById(R.id.lbl_value_draw);
//        mLblGd = (TextView) findViewById(R.id.lbl_value_goal_difference);
//        mLblLose = (TextView) findViewById(R.id.lbl_value_lose);
//        mLblPlayed = (TextView) findViewById(R.id.lbl_value_played);
//        mLblPoint = (TextView) findViewById(R.id.lbl_value_point);
//        mLblWin = (TextView) findViewById(R.id.lbl_value_win);
//        mLblEstablished = (TextView) findViewById(R.id.lbl_established);
//        mLblManager = (TextView) findViewById(R.id.lbl_manager);
//        mLblNickName = (TextView) findViewById(R.id.lbl_nick_name);
//        mLblStadium = (TextView) findViewById(R.id.lbl_stadium);
//        mBtnViewDetail = (Button) findViewById(R.id.btn_view_club_detail);
//
//        mBtnViewDetail.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(i);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(ClubDetailActivity.this,
//                            "The page is not available!", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        });
//    }
//
//    private void initData() {
////        mImageLoader.DisplayImage(currentClub.getmLogo(), mLogo);
//        mLblDraw.setText(currentClub.getmD());
//        mLblGd.setText(currentClub.getmGD());
//        mLblLose.setText(currentClub.getmL());
//        mLblPlayed.setText(currentClub.getmP());
//        mLblPoint.setText(currentClub.getmPTS());
//        mLblWin.setText(currentClub.getmW());
//
//
//
//
//        if(!currentClub.getEstablished().isEmpty() && !currentClub.getEstablished().equals("n/a")){
//            mLblEstablished.setText(String.format(getString(R.string.text_established), currentClub.getEstablished()));
//        } else {
//            mLblEstablished.setVisibility(View.GONE);
//        }
//
//        if(!currentClub.getManager().isEmpty() && !currentClub.getManager().equals("n/a")){
//            mLblManager.setText(String.format(getString(R.string.text_manager), currentClub.getManager()));
//        } else {
//            mLblManager.setVisibility(View.GONE);
//        }
//
//        if(!currentClub.getNickName().isEmpty() && !currentClub.getNickName().equals("n/a")){
//            mLblNickName.setText(String.format(getString(R.string.text_nickname), currentClub.getNickName()));
//        } else {
//            mLblNickName.setVisibility(View.GONE);
//        }
//        if(!currentClub.getStadium().isEmpty() && !currentClub.getStadium().equals("n/a")){
//            mLblStadium.setText(String.format(getString(R.string.text_stadium), currentClub.getStadium()));
//        } else {
//            mLblStadium.setVisibility(View.GONE);
//        }
//
//
//        url = currentClub.getmClubUrl();
//    }
}
