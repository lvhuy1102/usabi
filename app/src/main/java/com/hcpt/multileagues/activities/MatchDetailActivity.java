package com.hcpt.multileagues.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
//import android.util.TypedValue;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.TabsMatchDetailAdapter;
import com.hcpt.multileagues.configs.Constants;
import com.hcpt.multileagues.configs.JsonConfigs;
//import com.hcpt.multileagues.fragments.LineupsFragment;
//import com.hcpt.multileagues.fragments.TimelineFragmentNew;
import com.hcpt.multileagues.json.utils.ParserUtils;
import com.hcpt.multileagues.modelmanager.ModelManager;
import com.hcpt.multileagues.modelmanager.ModelManagerListener;
import com.hcpt.multileagues.objects.MatchObj;
//import com.hcpt.multileagues.pagerslidingstabstrip.PagerSlidingTabStrip;
import com.hcpt.multileagues.utilities.DateTimeUtility;
import com.hcpt.multileagues.widget.textview.TextViewRobotoRegular;

public class MatchDetailActivity extends AppCompatActivity {

    public static MatchObj currentMatch;

    private TabLayout tabs;
    public ViewPager pager;
    private TabsMatchDetailAdapter adapter;
    private TextView txtDay, txtStatus, txtStadium, txtTeam1, txtTeam2;
    private TextView txtScoreTeam1, txtScoreTeam2, tvMinute, tvDate;
    private TextView tvHomePen, tvAwayPen;
    private ImageView iv_menu, iv_refresh;
    private android.support.v7.widget.Toolbar toolbar;
    private LinearLayout lnlToolbar;
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_details_activity_new);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }
        ProgressBar progressBar = findViewById(R.id.animate_progress_bar);
        progressBar.setMax(100);
        progressBar.setProgress(60);

        // Enable Up button.
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.dash_board);
            getSupportActionBar().hide();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        initViews();
        initData();
        initControl();
        toolbar = findViewById(R.id.toolbar);
        lnlToolbar = toolbar.findViewById(R.id.lnlToolbar);
        tvTitle = findViewById(R.id.tvTitle);
        toolbar.setBackgroundColor(getResources().getColor(R.color.tranparent));
        lnlToolbar.setBackgroundColor(getResources().getColor(R.color.tranparent));
        iv_menu.setVisibility(View.VISIBLE);
        iv_refresh.setVisibility(View.VISIBLE);
        tvTitle.setText("MATCH DETAILS");
        // Admob
        LeagueActivity.initBannerAdsOnActivity(this, R.id.ll_admob);
    }

    private void initViews() {
        txtScoreTeam1 = (TextView) findViewById(R.id.txtScoreTeam1MatchDetail);
        txtScoreTeam2 = (TextView) findViewById(R.id.txtScoreTeam2MatchDetail);
        txtTeam1 = findViewById(R.id.txtTeam1MatchDetail);
//        txtTeam1.setSelected(true);
        txtTeam2 = findViewById(R.id.txtTeam2MatchDetail);
//        txtTeam2.setSelected(true);
        txtStadium = findViewById(R.id.txtStadiumMatchDetail);
        tvMinute = (TextView) findViewById(R.id.tv_minute);
        tvDate = (TextView) findViewById(R.id.tv_date);
        iv_menu = findViewById(R.id.iv_menu);
        iv_refresh = findViewById(R.id.iv_refresh);
        tvHomePen = (TextView) findViewById(R.id.tv_home_pen);
        tvAwayPen = (TextView) findViewById(R.id.tv_away_pen);
    }

    // Get current match info.
    private void getCurrentMatch(String mMatchId) {
        ModelManager.getMatchById(MatchDetailActivity.this, mMatchId, true,
                new ModelManagerListener() {

                    @Override
                    public void onSuccess(Object object) {
                        // TODO Auto-generated method stub
                        String json = (String) object;
                        currentMatch = ParserUtils.parserMatchDetail(json);
                        Constants.MATCH_STATUS = currentMatch.getmMatchStatus();
                        initDataMatch();
                        initTabs(currentMatch.getmMatchStatus());
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    // Get current match info.
    private void refresh(String mMatchId) {
        ModelManager.getMatchById(MatchDetailActivity.this, mMatchId, true,
                new ModelManagerListener() {

                    @Override
                    public void onSuccess(Object object) {
                        // TODO Auto-generated method stub
                        String json = (String) object;
                        currentMatch = ParserUtils.parserMatchDetail(json);
                        Constants.MATCH_STATUS = currentMatch.getmMatchStatus();
                        initDataMatch();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    private void initData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (intent != null && action != null) {
            Bundle b = intent.getBundleExtra(Constants.DATA);
            String matchId = b.getString(Constants.MATCH_ID);
            if (matchId != null) {
                currentMatch.setmMatchId(matchId);
                getCurrentMatch(matchId);
            } else {
                initDataMatch();
                initTabs(currentMatch.getmMatchStatus());
            }
        } else {
            initDataMatch();
            initTabs(currentMatch.getmMatchStatus());
        }
    }

    private void initDataMatch() {
        txtTeam1.setText(currentMatch.getmHomeName());
        txtTeam2.setText(currentMatch.getmAwayName());
        String homePen = "";
        String awayPen = "";
        if (currentMatch.getmMatchStatus().equals(JsonConfigs.MATCH_STATUS_FINISHED)) {
            if (currentMatch.getHomePen() != null && !currentMatch.getHomePen().trim().isEmpty()
                    && currentMatch.getHomePen().trim().length() != 0 && !currentMatch.getHomePen().equals("null")
                    && !currentMatch.getHomePen().trim().equals("")) {
                homePen = "(" + currentMatch.getHomePen() + ")";
            }
            if (currentMatch.getAwayPen() != null && !currentMatch.getAwayPen().trim().isEmpty()
                    && currentMatch.getAwayPen().trim().length() != 0 && !currentMatch.getAwayPen().equals("null")
                    && !currentMatch.getAwayPen().trim().equals("")) {
                awayPen = "(" + currentMatch.getAwayPen() + ")";
            }
        }
        if (currentMatch.getmMatchStatus().equals(JsonConfigs.MATCH_STATUS_NOT_STARTED)) {
            txtScoreTeam1.setText("?");
            txtScoreTeam2.setText("?");
            tvHomePen.setText(homePen);
            tvAwayPen.setText(awayPen);
        } else {
            txtScoreTeam1.setText(currentMatch.getmHomeScore());
            txtScoreTeam2.setText(currentMatch.getmAwayScore());
            tvHomePen.setText(homePen);
            tvAwayPen.setText(awayPen);
        }
        String status = currentMatch.getmMatchStatus();
        String strTime = "";
        String date = DateTimeUtility.convertTimeStampToDate(currentMatch.getmTime(), "dd MMM");
        tvDate.setText(date);
        if (currentMatch.getmMinute().trim().equalsIgnoreCase(Constants.POSTPONDED)) {
            strTime = Constants.POSTPONDED;
        } else if (currentMatch.getmMinute().trim().equalsIgnoreCase(Constants.CANCELLED)) {
            strTime = Constants.CANCELLED;
        } else if (currentMatch.getmMinute().trim().equalsIgnoreCase(Constants.ABANDONED)) {
            strTime = Constants.ABANDONED;
        } else {
            if (status.equals(JsonConfigs.MATCH_STATUS_NOT_STARTED)) {
                strTime = DateTimeUtility.convertTimeStampToDate(currentMatch.getmTime(), "HH:mm");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvMinute.setTextColor(getColor(R.color.white));
                    tvDate.setTextColor(getColor(R.color.white));
                } else {
                    tvMinute.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tvDate.setTextColor(ContextCompat.getColor(this, R.color.white));
                }
            } else if (status.equals(JsonConfigs.MATCH_STATUS_ACTIVE)) {
                strTime = getResources().getString(R.string.live) + ", " + currentMatch.getmMinute() + "'";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvMinute.setTextColor(getColor(R.color.white));
                    tvDate.setTextColor(getColor(R.color.white));
                } else {
                    tvMinute.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tvDate.setTextColor(ContextCompat.getColor(this, R.color.white));
                }
            } else if (status.equals(JsonConfigs.MATCH_STATUS_FINISHED)) {
                strTime = getResources().getString(R.string.finish);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvMinute.setTextColor(getColor(R.color.white));
                    tvDate.setTextColor(getColor(R.color.white));
                } else {
                    tvMinute.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tvDate.setTextColor(ContextCompat.getColor(this, R.color.white));
                }
            }
        }

        tvMinute.setText(strTime);
        txtStadium.setText(currentMatch.getmStadium());
    }

    private void initControl() {
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.IS_PUSH) {
                    if (Constants.IS_APP_RUNNING) {
                        Constants.IS_PUSH = false;
                        finish();
                    } else {
                        Intent i = new Intent(MatchDetailActivity.this, MainActivity.class);
                        startActivity(i);
                        Constants.IS_PUSH = false;
                        finish();
                    }
                } else {
                    finish();
                }
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(currentMatch.getmMatchId());
//                getCurrentMatch(currentMatch.getmMatchId());
                Intent intent = new Intent();
                intent.setAction(Constants.REFRESH_DATA);
                sendBroadcast(intent);
            }
        });
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Hide menus.
//        MenuItem item = menu.findItem(R.id.action_filter);
//        item.setVisible(false);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public void onBackPressed() {
        if (Constants.IS_PUSH) {
            if (Constants.IS_APP_RUNNING) {
                Constants.IS_PUSH = false;
                super.onBackPressed();
            } else {
                Intent i = new Intent(MatchDetailActivity.this, MainActivity.class);
                startActivity(i);
                Constants.IS_PUSH = false;
                super.onBackPressed();
            }
        } else {

            super.onBackPressed();
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Create tabs.
    private void initTabs(String matchStatus) {
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new TabsMatchDetailAdapter(manager, this, matchStatus);
        pager.setOffscreenPageLimit(2);
//        if (pager.getAdapter() == null) {
        pager.setAdapter(null);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setTabsFromPagerAdapter(adapter);
    }
}