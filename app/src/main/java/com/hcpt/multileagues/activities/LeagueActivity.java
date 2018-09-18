package com.hcpt.multileagues.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.HomeMainAdapter;
import com.hcpt.multileagues.adapters.LeagueAdapter;
import com.hcpt.multileagues.adapters.TabsMainAdapter;
import com.hcpt.multileagues.adapters.TabsMainCLAdapter;
import com.hcpt.multileagues.configs.Args;
import com.hcpt.multileagues.configs.Constants;
import com.hcpt.multileagues.configs.FruitySharedPreferences;
import com.hcpt.multileagues.configs.GlobalFunctions;
import com.hcpt.multileagues.configs.GlobalValue;
import com.hcpt.multileagues.configs.WebservicesConfigs;
import com.hcpt.multileagues.database.DatabaseUtility;
import com.hcpt.multileagues.json.utils.ParserUtils;
import com.hcpt.multileagues.modelmanager.ModelManager;
import com.hcpt.multileagues.modelmanager.ModelManagerListener;
import com.hcpt.multileagues.network.NetworkUtility;
import com.hcpt.multileagues.objects.APIObj;
import com.hcpt.multileagues.objects.LeagueObj;

import java.util.ArrayList;

public class LeagueActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView mLvLeague;
    private TabLayout tabs;
    public ViewPager pager;
    private TabsMainAdapter tabsMainAdapter;
    private HomeMainAdapter mHomeMainAdapter;
    private TabsMainCLAdapter tabsMainCLAdapter;
    private Menu mMenu;
    private TextView tvTitile;
    private TextView border_bottom_tab;
    private LeagueAdapter mLeagueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.IS_APP_RUNNING = true;
        if (!NetworkUtility.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.msg_using_offline_data), Toast.LENGTH_LONG).show();
        }

        // Set language from scratch
        if (GlobalValue.prefs == null) {
            GlobalValue.prefs = new FruitySharedPreferences(this);
        }
        if (!GlobalValue.prefs.getStringValue(Args.LANGUAGE).isEmpty()) {
            GlobalFunctions.changeLang(this, GlobalValue.prefs.getStringValue(Args.LANGUAGE));
        } else {
            GlobalValue.prefs.putStringValue(Args.LANGUAGE, Constants.ENGLISH);
        }

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
//        }

        initUI();

        initDashboard();

        // Create league drawer.
        createLeagueDrawer();

        // Check google play services sdk.
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            // Admob
            initBannerAdsOnActivity(this, R.id.ll_admob);

            // Google analytics
        } else {
            GooglePlayServicesUtil.getErrorDialog(
                    GooglePlayServicesUtil.isGooglePlayServicesAvailable(this), this, 1).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Bundle bundle = intent.getExtras();
//        if (bundle.getBoolean("pushNotification")) {
//            pager.setCurrentItem(1);
//        }
    }

    // Exit app when Back button is clicked.


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (GlobalValue.arrRankClubs != null) {
            GlobalValue.arrRankClubs.clear();
            GlobalValue.arrRankClubs = null;
        }
    }

    private void initUI() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        border_bottom_tab = (TextView) findViewById(R.id.border_bottom_tab);
        tabs.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    private void initChampionsLeague() {
        // Show tabs
        tabs.setVisibility(View.VISIBLE);
        border_bottom_tab.setVisibility(View.VISIBLE);
        tabsMainCLAdapter = new TabsMainCLAdapter(getSupportFragmentManager(), this);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(tabsMainCLAdapter);
    }

    private void initSingleLeague() {
        // Show tabs
        tabs.setVisibility(View.VISIBLE);
        border_bottom_tab.setVisibility(View.VISIBLE);
        tabsMainAdapter = new TabsMainAdapter(getSupportFragmentManager(), this);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(tabsMainAdapter);
    }

    private void initDashboard() {
        // Hide tabs
        tabs.setVisibility(View.GONE);
        border_bottom_tab.setVisibility(View.GONE);
//        mHomeMainAdapter = new HomeMainAdapter(getSupportFragmentManager());
        pager.setAdapter(mHomeMainAdapter);

    }

    public static void initBannerAdsOnActivity(final Activity act, int layoutBannerAdsId) {
        AdView adView = new AdView(act);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(act.getString(R.string.key_google_admob_banner));

        LinearLayout layout = (LinearLayout) act.findViewById(layoutBannerAdsId);
        if (layout != null) {
            layout.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }


    @SuppressLint("NewApi")
    private void createLeagueDrawer() {
        // Init controls.

        mLvLeague.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get filter menu
                MenuItem filterMenu = mMenu.findItem(R.id.action_filter);


                // Set title

                if (GlobalValue.arrLeague.get(position).getmType().equals(LeagueObj.DASHBOARD)) {
                    // Show filter menu
                    setTitleByLeague(position, "");
                    filterMenu.setVisible(true);
                    initDashboard();
                } else {
                    // Get league id.
//                    GlobalValue.leagueId = GlobalValue.arrLeague.get(position).getmId();

                    // Hide filter menu
                    filterMenu.setVisible(false);

                    if (Integer.parseInt(GlobalValue.arrLeague.get(position).getmType()) == 1) {
                        if (GlobalValue.arrLeague.get(position).getDemo().equals("0")) {
                            setTitleByLeague(position, "");
                            initSingleLeague();
                        } else {
                            if (position == 1 || position == 2) {
                                setTitleByLeague(position, "");
                                initSingleLeague();
                            } else {
                                showAlertDialog();
                            }

                        }

                    } else {
                        if (GlobalValue.arrLeague.get(position).getDemo().equals("0")) {
                            setTitleByLeague(position, "");
                            initChampionsLeague();
                        } else {
                            showAlertDialog();
                        }

                    }
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LeagueActivity.this);
        builder.setTitle(getResources().getString(R.string.alert));
        builder.setMessage(getResources().getString(R.string.demo_message));
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    private void getLeagues() {
        if (GlobalValue.arrLeague == null) {
            GlobalValue.arrLeague = new ArrayList<>();
        } else {
            GlobalValue.arrLeague.clear();
        }
        if (NetworkUtility.getInstance(this).isNetworkAvailable()) {
            getLeaguesFromAPI();
        } else {
            getLeaguesFromDB();
        }
    }

    private void getLeaguesFromDB() {
        APIObj apiInfos = DatabaseUtility.getResuftFromApi(this, WebservicesConfigs.URL_GET_LEAGUE);
        if (apiInfos != null) {
            if (GlobalValue.arrLeague != null) {
                GlobalValue.arrLeague.clear();
            }

            String json = apiInfos.getmResult();
            Log.e(TAG, json);

            ArrayList<LeagueObj> arr = ParserUtils.parserLeagues(json);
            GlobalValue.arrLeague.add(new LeagueObj("0", getResources()
                    .getString(R.string.close_matches), getResources()
                    .getString(R.string.close_matches), getResources()
                    .getString(R.string.close_matches), getResources()
                    .getString(R.string.close_matches), getResources()
                    .getString(R.string.close_matches), getResources()
                    .getString(R.string.close_matches), getResources()
                    .getString(R.string.close_matches), LeagueObj.DASHBOARD));
            GlobalValue.arrLeague.addAll(arr);
//            if (arr != null && arr.size() > 0) {
//                for (int i = 0; i < arr.size(); i++) {
//                    GlobalValue.arrLeague.add(arr.get(i));
//                    if (i == GlobalValue.numberLeague) break;
//                }
//            }

            // Bind data for listview.
//            mLeagueAdapter = new LeagueAdapter(LeagueActivity.this, GlobalValue.arrLeague);
//            mLvLeague.setAdapter(mLeagueAdapter);

            // Set title when the app is started.
            setTitleByLeague(0, "");
        }
    }

    private void getLeaguesFromAPI() {
        ModelManager.getLeagues(this, false, new ModelManagerListener() {

            @Override
            public void onSuccess(Object object) {
                if (GlobalValue.arrLeague != null) {
                    GlobalValue.arrLeague.clear();
                }

                String json = (String) object;
                Log.e(TAG, json);
                ArrayList<LeagueObj> arr = ParserUtils.parserLeagues(json);
                GlobalValue.arrLeague.add(new LeagueObj("0", getResources()
                        .getString(R.string.close_matches), getResources()
                        .getString(R.string.close_matches), getResources()
                        .getString(R.string.close_matches), getResources()
                        .getString(R.string.close_matches), getResources()
                        .getString(R.string.close_matches), getResources()
                        .getString(R.string.close_matches), "", LeagueObj.DASHBOARD));
                GlobalValue.arrLeague.addAll(arr);
//                if (arr != null && arr.size() > 0) {
//                    for (int i = 0; i < arr.size(); i++) {
//                        GlobalValue.arrLeague.add(arr.get(i));
//                        if (i == GlobalValue.numberLeague) break;
//                    }
//                }

//                mLeagueAdapter = new LeagueAdapter(LeagueActivity.this, GlobalValue.arrLeague);
//                mLvLeague.setAdapter(mLeagueAdapter);

                // Set title when the app is started.
                setTitleByLeague(0, "");
            }

            @Override
            public void onError() {
            }
        });
    }

    public void setTitleByLeague(int pos, String date) {
        try {
            if (pos == 0) {
                if (!date.equals("")) {
                    tvTitile.setText(date);
                } else {
//                    if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals("")
//                            || GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.ENGLISH)) {
//                        tvTitile.setText(GlobalValue.arrLeague.get(pos).getmName());
//                    } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.CHINESE)) {
//                        tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameChinese());
//                    } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.GERMAN)) {
//                        tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameGerman());
//                    } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.SPANISH)) {
//                        tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameSpanish());
//                    } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.FRENCH)) {
//                        tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameFrench());
//                    } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.ITALIAN)) {
//                        tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameItalian());
//                    }
                    tvTitile.setText(getString(R.string.home));
                }
            } else {
                if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals("")
                        || GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.ENGLISH)) {
                    tvTitile.setText(GlobalValue.arrLeague.get(pos).getmName());
                } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.CHINESE)) {
                    tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameChinese());
                } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.GERMAN)) {
                    tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameGerman());
                } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.SPANISH)) {
                    tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameSpanish());
                } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.FRENCH)) {
                    tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameFrench());
                } else if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).equals(Constants.ITALIAN)) {
                    tvTitile.setText(GlobalValue.arrLeague.get(pos).getmNameItalian());
                }
            }


        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
