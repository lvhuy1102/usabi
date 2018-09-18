package com.hcpt.multileagues.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.activities.MainActivity;
import com.hcpt.multileagues.activities.ProfileManagerActivity;
import com.hcpt.multileagues.adapters.FavTeamAdapter;
import com.hcpt.multileagues.configs.Args;
import com.hcpt.multileagues.configs.Constants;
import com.hcpt.multileagues.configs.FruitySharedPreferences;
import com.hcpt.multileagues.configs.GlobalValue;
import com.hcpt.multileagues.configs.WebservicesConfigs;
import com.hcpt.multileagues.database.DatabaseUtility;
import com.hcpt.multileagues.json.utils.ParserUtils;
import com.hcpt.multileagues.modelmanager.ModelManager;
import com.hcpt.multileagues.modelmanager.ModelManagerListener;
import com.hcpt.multileagues.network.NetworkUtility;
import com.hcpt.multileagues.objects.APIObj;
import com.hcpt.multileagues.objects.FavoriteObj;
import com.hcpt.multileagues.objects.SettingObj;

import java.util.ArrayList;

public class SettingLeftFragment extends Fragment {
    private CheckBox mChkAutoRefresh, mChkGetNotification;
    private SeekBar mSkbar;
    private LinearLayout mLblChooseFav;
    private Button mBtnMoreApps, mBtnAboutUs, mBtnLanguage;
    private ArrayList<FavoriteObj> mArrFavorite;
    private SettingObj mSettingObj;
    private String mNotificationStatus;
    private String mCurrentLang;
    private LinearLayout layoutNotification;
    private RecyclerView rcvFavTeam;
    private FavTeamAdapter favTeamAdapter;
    private CheckBox chkAll;
    private TextView tvTime, tvStatus;
    private LinearLayout layoutTime;
    private ImageView imageView;
    private View view;
    private ImageView imgAvatar;
    private TextView tvName, tvProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (GlobalValue.prefs == null) {
            GlobalValue.prefs = new FruitySharedPreferences(getActivity());
        }
        setHasOptionsMenu(true);
        if (GlobalValue.prefs.getStringValue(Args.LANGUAGE).isEmpty()) {
            GlobalValue.prefs.putStringValue(Args.LANGUAGE, Constants.ENGLISH);
        }
        mCurrentLang = GlobalValue.prefs.getStringValue(Args.LANGUAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.setting_left_fragment, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.tvTitle);
        textView.setText("SETING");
        tvName = view.findViewById(R.id.tvName);
        tvProfile = view.findViewById(R.id.tvProfile);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileManagerActivity.class);
                getActivity().startActivity(intent);
            }
        });
        initUI();
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(getActivity(), getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);

        // Hide menus.
        MenuItem item = menu.findItem(R.id.action_filter);
        item.setVisible(false);
        item = menu.findItem(R.id.action_refresh);
        item.setVisible(false);
    }

    private void initUI() {
        mSkbar = (SeekBar) view.findViewById(R.id.skbar_minute_refresh);
        mChkAutoRefresh = (CheckBox) view.findViewById(R.id.chk_auto_refresh);
        mChkGetNotification = (CheckBox) view.findViewById(R.id.chk_getNotification);
        mLblChooseFav = (LinearLayout) view.findViewById(R.id.lbl_fav_teams);
        mBtnLanguage = (Button) view.findViewById(R.id.btn_language);
        layoutNotification = (LinearLayout) view.findViewById(R.id.layout_notification);
        rcvFavTeam = (RecyclerView) view.findViewById(R.id.rcl_team);
        chkAll = (CheckBox) view.findViewById(R.id.chk_all);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
        layoutTime = (LinearLayout) view.findViewById(R.id.layou_time);
        imageView = (ImageView) view.findViewById(R.id.image);
        initControl();

    }

    private void initControl() {
        mChkAutoRefresh
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            layoutTime.setVisibility(View.VISIBLE);
                            mSkbar.setVisibility(View.VISIBLE);
                            if (GlobalValue.prefs.getIntValue(Args.KEY_UI_PROGRESS_VALUE) == 0) {
                                GlobalValue.prefs.putIntValue(Args.KEY_UI_PROGRESS_VALUE, 1);
                            }
                            tvTime.setText(GlobalValue.prefs.getIntValue(Args.KEY_UI_PROGRESS_VALUE) + " mins");
                        } else {
                            layoutTime.setVisibility(View.GONE);
                            mSkbar.setVisibility(View.GONE);
                            mChkAutoRefresh.setText(getActivity().getResources().getString(
                                    R.string.text_auto_refresh));

                        }

                        // Put reference.
                        GlobalValue.prefs.putBooleanValue(Args.KEY_UI_AUTO_REFRESH, isChecked);
                    }
                });

        mSkbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Put reference(minute)
                if (seekBar.getProgress() == 0) {
                    GlobalValue.prefs.putIntValue(Args.KEY_UI_PROGRESS_VALUE, 1);
                } else {
                    GlobalValue.prefs.putIntValue(Args.KEY_UI_PROGRESS_VALUE, seekBar.getProgress());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress == 0) {
                    progress = progress + 1;
                }
//                mChkAutoRefresh.setText(String.format(getString(R.string.text_auto_refresh_after_minute), progress));
                tvTime.setText(progress + " mins");
            }
        });


    }
}
