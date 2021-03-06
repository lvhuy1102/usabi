package com.hcpt.multileagues.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.activities.MatchDetailActivity;
import com.hcpt.multileagues.activities.MatchDetailNotPlayed;
import com.hcpt.multileagues.adapters.LobbyAdapter;
import com.hcpt.multileagues.configs.Constants;
import com.hcpt.multileagues.configs.WebservicesConfigs;
import com.hcpt.multileagues.database.DatabaseUtility;
import com.hcpt.multileagues.interfaces.OnNotificationButtonClickListener;
import com.hcpt.multileagues.json.utils.ParserUtils;
import com.hcpt.multileagues.modelmanager.ModelManager;
import com.hcpt.multileagues.modelmanager.ModelManagerListener;
import com.hcpt.multileagues.network.NetworkUtility;
import com.hcpt.multileagues.objects.APIObj;
import com.hcpt.multileagues.objects.AlarmMatch;
import com.hcpt.multileagues.objects.MatchObj;
import com.hcpt.multileagues.services.AlarmReceiver;
import com.hcpt.multileagues.utilities.DateTimeUtility;
import com.hcpt.multileagues.utilities.Debug;

import java.util.ArrayList;
import java.util.Calendar;

public class UpcommingFragment extends Fragment {
    private View view;
    private ArrayList<MatchObj> mArrMatch;
    private String mDateTimeStamp = "";
    private Calendar mCal;
    private static MatchObj mMatchObj;
    private RecyclerView rclMatchs;
    private LobbyAdapter lobbyAdapter;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    private AlarmMatch alarmMatchPrevious;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private boolean isFistTime = true;
    public static UpcommingFragment upcommingFragment;

    public static UpcommingFragment newInstance() {
        if (upcommingFragment == null)
            upcommingFragment = new UpcommingFragment();
        return upcommingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allow display option menu in this fragment.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Debug.e("LOBBY creatviiew");

        view = inflater.inflate(R.layout.fragment_upcomming, container, false);

        mCal = Calendar.getInstance();
        initUI();
        initData("1");
        mListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private void mListener() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.REFRESH)) {
//                    lobbyAdapter.notifyDataSetChanged();
                }
            }
        };
        filter = new IntentFilter();
        filter.addAction(Constants.REFRESH);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);

        // Show filter menu.
        MenuItem item = menu.findItem(R.id.action_filter);
        item.setVisible(true);
        menu.findItem(R.id.action_save).setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            if (isFistTime) {
                initData("1");
            } else {
                initData("0");
            }

        } else if (id == R.id.action_filter) {
//            showDatePicker();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        rclMatchs = view.findViewById(R.id.rclMatchs);

        // Should call this method by the end of declaring UI.
//        initControls();
    }

//    // Init widget controls.
//    private void initControls() {
//        rclMatchs.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // Get match which is selected.
//                mMatchObj = mArrMatch.get(position);
//                //test
//                // MatchDetailActivity.matchId = mMatchObj.getmMatchId();
//                MatchDetailActivity.currentMatch = mMatchObj;
//                MatchDetailNotPlayed.currentMatch = mMatchObj;
//
//                // Check status to set screen.
////                if (mMatchObj.getmMatchStatus().equals(JsonConfigs.MATCH_STATUS_NOT_STARTED)) {
////                startActivity(new Intent(getActivity(), MatchDetailNotPlayed.class));
////                } else {
//                startActivity(new Intent(getActivity(), MatchDetailActivity.class));
////                }
//            }
//        });
//    }

    private void initData(String strDefault) {
        // Get today if has not filtered.
        if (mDateTimeStamp.equals("")) {
            if (strDefault.equals("1")) {
                mDateTimeStamp = DateTimeUtility
                        .convertTimeStampToDate(Calendar.getInstance()
                                .getTimeInMillis() + "");
            } else {
                mDateTimeStamp = DateTimeUtility
                        .convertTimeStampToStartOfDate(Calendar.getInstance()
                                .getTimeInMillis() + "");
            }

        }

        if (NetworkUtility.getInstance(getActivity()).isNetworkAvailable()) {
            getDashboardFromApi(strDefault);
        } else {
            getDashboardFromDB();
        }
    }

    private void getDashboardFromDB() {
        final String strDate = DateTimeUtility.convertTimeStampToDate(
                mDateTimeStamp, "yyyyMMdd");
        APIObj apiInfos = DatabaseUtility.getResuftFromApi(getActivity(),
                (WebservicesConfigs.URL_GET_DASHBOARD + strDate));
        if (apiInfos != null) {
            String json = apiInfos.getmResult();

            mArrMatch = ParserUtils.parserDashboard(json);

            if (mArrMatch.size() > 0) {
                lobbyAdapter = new LobbyAdapter(getActivity(), mArrMatch);
                rclMatchs.setAdapter(lobbyAdapter);
//                lobbyAdapter.setOnNotificationButtonClickListener(new OnNotificationButtonClickListener() {
//                    @Override
//                    public void onNotificationButtonClick(MatchObj matchObj) {
//                        mMatchObj = matchObj;
//                        MatchDetailActivity.currentMatch = mMatchObj;
//                        MatchDetailNotPlayed.currentMatch = mMatchObj;
//                        showDialogAlarm(matchObj.getmTime(), "", matchObj);
//                    }
//                });
            } else {
                // Show no data view
            }
        }
    }

    private void getDashboardFromApi(String strDefault) {
        ModelManager.getDashboard(getActivity(), mDateTimeStamp, strDefault,
                true, new ModelManagerListener() {

                    @Override
                    public void onSuccess(Object object) {
                        String json = (String) object;


                        mArrMatch = ParserUtils.parserDashboard(json);

                        if (mArrMatch.size() > 0) {
                            // Hide no data view
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rclMatchs.setLayoutManager(layoutManager);
                            lobbyAdapter = new LobbyAdapter(getActivity(), mArrMatch);
                            rclMatchs.setAdapter(lobbyAdapter);
//                            lobbyAdapter.setOnNotificationButtonClickListener(new OnNotificationButtonClickListener() {
//                                @Override
//                                public void onNotificationButtonClick(MatchObj matchObj) {
//                                    mMatchObj = matchObj;
//                                    MatchDetailActivity.currentMatch = mMatchObj;
//                                    MatchDetailNotPlayed.currentMatch = mMatchObj;
//                                    showDialogAlarm(matchObj.getmTime(), "", matchObj);
//                                }
//                            });
//                            ((MainActivity) getActivity()).setTitleByLeague(0, "");
                        } else {
                            // Show no data view

                        }
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                if (view.isShown()) {
                    mCal.set(year, monthOfYear, dayOfMonth);

                    mDateTimeStamp = DateTimeUtility
                            .convertTimeStampToStartOfDate(mCal
                                    .getTimeInMillis() + "");
                    isFistTime = false;
                    initData("0");
                }
            }

        };

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                callBack, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                mCal.get(Calendar.DATE));
        datePicker.show();
    }

    //=======================================================
    //================================================
    private void showDialogAlarm(final String times, final String titleReminder, final MatchObj mMatchObj) {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(DateTimeUtility.convertTimeStampToDate(mMatchObj.getmTime(), "HH:mm EEE, yyyy-MM-dd"));
        dialog.setContentView(R.layout.dialog_settings_reminder);

        // Init dialog's controls.
        Button btnOk = (Button) dialog.findViewById(R.id.btn_reminder_ok);
        final Button btnCancel = (Button) dialog
                .findViewById(R.id.btn_reminder_cancel);
        final CheckBox chkBefore15 = (CheckBox) dialog
                .findViewById(R.id.chk_reminder_15);
        final CheckBox chkBefore30 = (CheckBox) dialog
                .findViewById(R.id.chk_reminder_30);
        final CheckBox chkBefore60 = (CheckBox) dialog
                .findViewById(R.id.chk_reminder_60);
        final CheckBox chkOnTime = (CheckBox) dialog
                .findViewById(R.id.chk_reminder_on_time);

        DatabaseUtility databaseUtility = new DatabaseUtility();
        if (databaseUtility.checkAlarmSetted(mMatchObj.getmMatchId(), getActivity())) {
            alarmMatchPrevious = databaseUtility.getAlarmMatchById(mMatchObj.getmMatchId(), getActivity()).get(0);
            chkOnTime.setChecked(alarmMatchPrevious.isOnTime());
            chkBefore15.setChecked(alarmMatchPrevious.isBefore15Min());
            chkBefore60.setChecked(alarmMatchPrevious.isBefore60Min());
            chkBefore30.setChecked(alarmMatchPrevious.isBefore30Min());
            if (alarmMatchPrevious.isOnTime() || alarmMatchPrevious.isBefore15Min() || alarmMatchPrevious.isBefore30Min() || alarmMatchPrevious.isBefore60Min()) {
                btnCancel.setVisibility(View.VISIBLE);
            }
        } else {
            String match_title = mMatchObj.getmHomeName() + " - " + mMatchObj.getmAwayName();
            alarmMatchPrevious = new AlarmMatch(mMatchObj.getmMatchId(), match_title, mMatchObj.getmTime(), false, false, false, false);
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlarmMatch item = new AlarmMatch();
                item.setTime(mMatchObj.getmTime());
                item.setMatchId(mMatchObj.getmMatchId());
                item.setMatchTitle(mMatchObj.getmHomeName() + " - " + mMatchObj.getmAwayName());
                item.setOnTime(chkOnTime.isChecked());
                item.setBefore15Min(chkBefore15.isChecked());
                item.setBefore30Min(chkBefore30.isChecked());
                item.setBefore60Min(chkBefore60.isChecked());
                if (chkOnTime.isChecked() || chkBefore15.isChecked() || chkBefore30.isChecked() || chkBefore60.isChecked()) {
                    saveSettingToDatabase(item);
                } else {
                    // remove from database
                    removeSettingFromDatabase(mMatchObj.getmMatchId());
                }
                setOrCancelAlarm(item, alarmMatchPrevious);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void saveSettingToDatabase(AlarmMatch alarmMatch) {
        DatabaseUtility databaseUtility = new DatabaseUtility();
        databaseUtility.insertAlarmMatch(alarmMatch, getActivity());
        requestRefreshData();
//        mCallBack.changeDatabaseListener();

    }

    private void removeSettingFromDatabase(String matchId) {
        DatabaseUtility databaseUtility = new DatabaseUtility();
        databaseUtility.deleteAlarmMatch(matchId, getActivity());
        requestRefreshData();
//        mCallBack.changeDatabaseListener();
    }

    void requestRefreshData() {
        Intent intent = new Intent();
        intent.setAction(Constants.REFRESH);
        getActivity().sendBroadcast(intent);
    }

    //New notification Local
    private void setOrCancelAlarm(AlarmMatch nowAlarmMatch, AlarmMatch previosAlarmMatch) {
        Long TIME_ON_TIME = 0L;
        Long TIME_15_MINUTES = 15 * 60 * 1000L;
        Long TIME_30_MINUTES = 30 * 60 * 1000L;
        Long TIME_60_MINUTES = 60 * 60 * 1000L;

        if (previosAlarmMatch.isOnTime()) {
            if (!nowAlarmMatch.isOnTime()) cancelAlarm(1);
        } else {
            if (nowAlarmMatch.isOnTime()) setAlarm(TIME_ON_TIME, 1);
        }
        if (previosAlarmMatch.isBefore15Min()) {
            if (!nowAlarmMatch.isBefore15Min()) cancelAlarm(2);
        } else {
            if (nowAlarmMatch.isBefore15Min()) setAlarm(TIME_15_MINUTES, 2);
        }

        if (previosAlarmMatch.isBefore30Min()) {
            if (!nowAlarmMatch.isBefore30Min()) cancelAlarm(3);
        } else {
            if (nowAlarmMatch.isBefore30Min()) setAlarm(TIME_30_MINUTES, 3);
        }

        if (previosAlarmMatch.isBefore60Min()) {
            if (!nowAlarmMatch.isBefore60Min()) cancelAlarm(4);
        } else {
            if (nowAlarmMatch.isBefore60Min()) setAlarm(TIME_60_MINUTES, 4);
        }


    }

    private void setAlarm(Long timeToSet, int requestCode) {

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra(Constants.KEY_TITLE_ALARM, alarmMatchPrevious.getMatchTitle());
        intent.putExtra(Constants.KEY_MESSAE_ALARM, DateTimeUtility.convertTimeStampToHours(alarmMatchPrevious.getTime()));
        pendingIntent = PendingIntent.getBroadcast(getActivity(), (Integer.parseInt(alarmMatchPrevious.getMatchId()) + requestCode), intent, PendingIntent.FLAG_ONE_SHOT);
        Long timeOfMatch = Long.parseLong(alarmMatchPrevious.getTime());

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeOfMatch * 1000L - timeToSet, pendingIntent);
    }

    private void cancelAlarm(int requestCode) {
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra(Constants.KEY_TITLE_ALARM, alarmMatchPrevious.getMatchTitle());
        Log.d("ALARM", alarmMatchPrevious.getTime());
        intent.putExtra(Constants.KEY_MESSAE_ALARM, DateTimeUtility.convertTimeStampToHours(alarmMatchPrevious.getTime()));
        pendingIntent = PendingIntent.getBroadcast(getActivity(), (Integer.parseInt(alarmMatchPrevious.getMatchId()) + requestCode), intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.cancel(pendingIntent);
    }
//
//    private View view;
//    private ArrayList<MatchObj> mArrMatch;
//    private String mDateTimeStamp = "";
//    private ListView rclView;
//    private LobbyAdapter lobbyAdapter;
//    private static MatchObj mMatchObj;
//    private AlarmMatch alarmMatchPrevious;
//    private AlarmManager alarmManager;
//    private PendingIntent pendingIntent;
//    private boolean isFistTime = true;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        view = inflater.inflate(R.layout.fragment_lobby, container, false);
//        rclView = view.findViewById(R.id.rclView);
//        initData("1");
//        Debug.e("HELLO");
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        initData("1");
//    }
//
//    private void initData(String strDefault) {
//        // Get today if has not filtered.
//        if (mDateTimeStamp.equals("")) {
//            if (strDefault.equals("1")) {
//                mDateTimeStamp = DateTimeUtility
//                        .convertTimeStampToDate(Calendar.getInstance()
//                                .getTimeInMillis() + "");
//            } else {
//                mDateTimeStamp = DateTimeUtility
//                        .convertTimeStampToStartOfDate(Calendar.getInstance()
//                                .getTimeInMillis() + "");
//            }
//        }
//        if (NetworkUtility.getInstance(getActivity()).isNetworkAvailable()) {
//            getDashboardFromApi(strDefault);
//        } else {
//            getDashboardFromDB();
//        }
//    }
//
//    private void getDashboardFromDB() {
//        final String strDate = DateTimeUtility.convertTimeStampToDate(
//                mDateTimeStamp, "yyyyMMdd");
//        APIObj apiInfos = DatabaseUtility.getResuftFromApi(getActivity(),
//                (WebservicesConfigs.URL_GET_DASHBOARD + strDate));
//        if (apiInfos != null) {
//            String json = apiInfos.getmResult();
//            mArrMatch = ParserUtils.parserDashboard(json);
//
//            if (mArrMatch.size() > 0) {
//                lobbyAdapter = new LobbyAdapter(getActivity(),mArrMatch);
//                rclView.setAdapter(lobbyAdapter);
//                lobbyAdapter.setOnNotificationButtonClickListener(new OnNotificationButtonClickListener() {
//                    @Override
//                    public void onNotificationButtonClick(MatchObj matchObj) {
//                        mMatchObj = matchObj;
//                        MatchDetailActivity.currentMatch = mMatchObj;
//                        MatchDetailNotPlayed.currentMatch = mMatchObj;
//                        showDialogAlarm(matchObj.getmTime(), "", matchObj);
//                    }
//                });
//
//            }
//        }
//    }
//
//    private void getDashboardFromApi(String strDefault) {
//        ModelManager.getDashboard(getActivity(), mDateTimeStamp, strDefault,
//                true, new ModelManagerListener() {
//
//                    @Override
//                    public void onSuccess(Object object) {
//                        Debug.e("success");
//                        String json = (String) object;
//                        mArrMatch = ParserUtils.parserDashboard(json);
//                        lobbyAdapter = new LobbyAdapter(getActivity(),mArrMatch);
//                        rclView.setAdapter(lobbyAdapter);
//                        lobbyAdapter.setOnNotificationButtonClickListener(new OnNotificationButtonClickListener() {
//                            @Override
//                            public void onNotificationButtonClick(MatchObj matchObj) {
//                                mMatchObj = matchObj;
//                                MatchDetailActivity.currentMatch = mMatchObj;
//                                MatchDetailNotPlayed.currentMatch = mMatchObj;
//                                showDialogAlarm(matchObj.getmTime(), "", matchObj);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError() {
//                        // TODO Auto-generated method stub
//                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void showDialogAlarm(final String times, final String titleReminder, final MatchObj mMatchObj) {
//        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.setTitle(DateTimeUtility.convertTimeStampToDate(mMatchObj.getmTime(), "HH:mm EEE, yyyy-MM-dd"));
//        dialog.setContentView(R.layout.dialog_settings_reminder);
//
//        // Init dialog's controls.
//        Button btnOk = (Button) dialog.findViewById(R.id.btn_reminder_ok);
//        final Button btnCancel = (Button) dialog
//                .findViewById(R.id.btn_reminder_cancel);
//        final CheckBox chkBefore15 = (CheckBox) dialog
//                .findViewById(R.id.chk_reminder_15);
//        final CheckBox chkBefore30 = (CheckBox) dialog
//                .findViewById(R.id.chk_reminder_30);
//        final CheckBox chkBefore60 = (CheckBox) dialog
//                .findViewById(R.id.chk_reminder_60);
//        final CheckBox chkOnTime = (CheckBox) dialog
//                .findViewById(R.id.chk_reminder_on_time);
//
//        DatabaseUtility databaseUtility = new DatabaseUtility();
//        if (databaseUtility.checkAlarmSetted(mMatchObj.getmMatchId(), getActivity())) {
//            alarmMatchPrevious = databaseUtility.getAlarmMatchById(mMatchObj.getmMatchId(), getActivity()).get(0);
//            chkOnTime.setChecked(alarmMatchPrevious.isOnTime());
//            chkBefore15.setChecked(alarmMatchPrevious.isBefore15Min());
//            chkBefore60.setChecked(alarmMatchPrevious.isBefore60Min());
//            chkBefore30.setChecked(alarmMatchPrevious.isBefore30Min());
//            if (alarmMatchPrevious.isOnTime() || alarmMatchPrevious.isBefore15Min() || alarmMatchPrevious.isBefore30Min() || alarmMatchPrevious.isBefore60Min()) {
//                btnCancel.setVisibility(View.VISIBLE);
//            }
//        } else {
//            String match_title = mMatchObj.getmHomeName() + " - " + mMatchObj.getmAwayName();
//            alarmMatchPrevious = new AlarmMatch(mMatchObj.getmMatchId(), match_title, mMatchObj.getmTime(), false, false, false, false);
//        }
//
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//            }
//        });
//
//        btnOk.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                AlarmMatch item = new AlarmMatch();
//                item.setTime(mMatchObj.getmTime());
//                item.setMatchId(mMatchObj.getmMatchId());
//                item.setMatchTitle(mMatchObj.getmHomeName() + " - " + mMatchObj.getmAwayName());
//                item.setOnTime(chkOnTime.isChecked());
//                item.setBefore15Min(chkBefore15.isChecked());
//                item.setBefore30Min(chkBefore30.isChecked());
//                item.setBefore60Min(chkBefore60.isChecked());
//                if (chkOnTime.isChecked() || chkBefore15.isChecked() || chkBefore30.isChecked() || chkBefore60.isChecked()) {
//                    saveSettingToDatabase(item);
//                } else {
//                    // remove from database
//                    removeSettingFromDatabase(mMatchObj.getmMatchId());
//                }
//                setOrCancelAlarm(item, alarmMatchPrevious);
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
//
//
//    private void saveSettingToDatabase(AlarmMatch alarmMatch) {
//        DatabaseUtility databaseUtility = new DatabaseUtility();
//        databaseUtility.insertAlarmMatch(alarmMatch, getActivity());
//        requestRefreshData();
////        mCallBack.changeDatabaseListener();
//
//    }
//
//    private void removeSettingFromDatabase(String matchId) {
//        DatabaseUtility databaseUtility = new DatabaseUtility();
//        databaseUtility.deleteAlarmMatch(matchId, getActivity());
//        requestRefreshData();
////        mCallBack.changeDatabaseListener();
//    }
//
//    void requestRefreshData() {
//        Intent intent = new Intent();
//        intent.setAction(Constants.REFRESH);
//        getActivity().sendBroadcast(intent);
//    }
//
//    //New notification Local
//    private void setOrCancelAlarm(AlarmMatch nowAlarmMatch, AlarmMatch previosAlarmMatch) {
//        Long TIME_ON_TIME = 0L;
//        Long TIME_15_MINUTES = 15 * 60 * 1000L;
//        Long TIME_30_MINUTES = 30 * 60 * 1000L;
//        Long TIME_60_MINUTES = 60 * 60 * 1000L;
//
//        if (previosAlarmMatch.isOnTime()) {
//            if (!nowAlarmMatch.isOnTime()) cancelAlarm(1);
//        } else {
//            if (nowAlarmMatch.isOnTime()) setAlarm(TIME_ON_TIME, 1);
//        }
//        if (previosAlarmMatch.isBefore15Min()) {
//            if (!nowAlarmMatch.isBefore15Min()) cancelAlarm(2);
//        } else {
//            if (nowAlarmMatch.isBefore15Min()) setAlarm(TIME_15_MINUTES, 2);
//        }
//
//        if (previosAlarmMatch.isBefore30Min()) {
//            if (!nowAlarmMatch.isBefore30Min()) cancelAlarm(3);
//        } else {
//            if (nowAlarmMatch.isBefore30Min()) setAlarm(TIME_30_MINUTES, 3);
//        }
//
//        if (previosAlarmMatch.isBefore60Min()) {
//            if (!nowAlarmMatch.isBefore60Min()) cancelAlarm(4);
//        } else {
//            if (nowAlarmMatch.isBefore60Min()) setAlarm(TIME_60_MINUTES, 4);
//        }
//
//
//    }
//
//    private void setAlarm(Long timeToSet, int requestCode) {
//
//        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//        intent.putExtra(Constants.KEY_TITLE_ALARM, alarmMatchPrevious.getMatchTitle());
//        intent.putExtra(Constants.KEY_MESSAE_ALARM, DateTimeUtility.convertTimeStampToHours(alarmMatchPrevious.getTime()));
//        pendingIntent = PendingIntent.getBroadcast(getActivity(), (Integer.parseInt(alarmMatchPrevious.getMatchId()) + requestCode), intent, PendingIntent.FLAG_ONE_SHOT);
//        Long timeOfMatch = Long.parseLong(alarmMatchPrevious.getTime());
//
//        alarmManager.set(AlarmManager.RTC_WAKEUP, timeOfMatch * 1000L - timeToSet, pendingIntent);
//    }
//
//    private void cancelAlarm(int requestCode) {
//        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//        intent.putExtra(Constants.KEY_TITLE_ALARM, alarmMatchPrevious.getMatchTitle());
//        Log.d("ALARM", alarmMatchPrevious.getTime());
//        intent.putExtra(Constants.KEY_MESSAE_ALARM, DateTimeUtility.convertTimeStampToHours(alarmMatchPrevious.getTime()));
//        pendingIntent = PendingIntent.getBroadcast(getActivity(), (Integer.parseInt(alarmMatchPrevious.getMatchId()) + requestCode), intent, PendingIntent.FLAG_ONE_SHOT);
//        alarmManager.cancel(pendingIntent);
//    }


}
