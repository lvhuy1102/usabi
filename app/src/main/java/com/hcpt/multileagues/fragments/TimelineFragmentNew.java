package com.hcpt.multileagues.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.activities.MatchDetailActivity;
import com.hcpt.multileagues.adapters.HightlightAdapter;
import com.hcpt.multileagues.adapters.TimelineAdapter;
import com.hcpt.multileagues.configs.Constants;
import com.hcpt.multileagues.json.utils.ParserUtils;
import com.hcpt.multileagues.modelmanager.ModelManager;
import com.hcpt.multileagues.modelmanager.ModelManagerListener;
import com.hcpt.multileagues.objects.MatchObj;
import com.hcpt.multileagues.objects.Timeline;
import com.hcpt.multileagues.widget.textview.TextViewRobotoRegular;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineFragmentNew extends Fragment {
    private Context mContext;
    private RecyclerView rcvTimeline;
    private ArrayList<Timeline> arrTimeline = new ArrayList<Timeline>();
    private TimelineAdapter timelineAdapter;
    private HightlightAdapter hightlightAdapter;
    private TextView tvHightlight, tvCommentary;
    private TextViewRobotoRegular txtNoData;
    private String matchId;
    private String idTeamA, idTeamB;

    private MatchObj mMatchObj;
    private MatchObj mMatchDetailObj;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;

    public TimelineFragmentNew() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frm_timeline, container, false);
        initUI(v);
        mContext = getActivity();
        mMatchObj = MatchDetailActivity.currentMatch;
        matchId = MatchDetailActivity.currentMatch.getmMatchId();
        idTeamA = MatchDetailActivity.currentMatch.getmHome();
        idTeamB = MatchDetailActivity.currentMatch.getmAway();
        if (matchId != null && idTeamA != null && idTeamB != null) {
            initData();
        }
        mListener();
        tvCommentary.setTextColor(getResources().getColor(R.color.gray));
        tvCommentary.setBackgroundColor(getResources().getColor(R.color.white));
        tvHightlight.setTextColor(getResources().getColor(R.color.white));
        tvHightlight.setBackgroundColor(getResources().getColor(R.color.red));
        setHightlightandCommentary();
        return v;
    }

    private void initUI(View view) {

        rcvTimeline = (RecyclerView) view.findViewById(R.id.rcvTimeline);
        txtNoData = (TextViewRobotoRegular) view.findViewById(R.id.txtNotDataTimeline);
        tvHightlight = view.findViewById(R.id.tvHightlight);
        tvCommentary = view.findViewById(R.id.tvCommentary);
    }

    private void initData() {
        hightlightAdapter = new HightlightAdapter(mContext, arrTimeline, idTeamA, idTeamB);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        rcvTimeline.setLayoutManager(linearLayoutManager);
        rcvTimeline.setAdapter(hightlightAdapter);
        refreshData();
    }

    private void mListener() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.REFRESH_DATA)) {
                    refreshData();
                }
            }
        };
        filter = new IntentFilter();
        filter.addAction(Constants.REFRESH_DATA);
        mContext.registerReceiver(broadcastReceiver, filter);
    }

    public void refreshData() {
        ModelManager.getEventsByMatch(mContext, matchId, true, new ModelManagerListener() {
            @Override
            public void onError() {
            }

            @Override
            public void onSuccess(Object object) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject((String) object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrTimeline.clear();
                arrTimeline.addAll(ParserUtils.parserTimelines(jsonObject));
//                timelineAdapter.notifyDataSetChanged();
                checkNoData();
            }
        });

    }

    private void checkNoData() {

        if (arrTimeline.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else {
            txtNoData.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (broadcastReceiver != null)
            mContext.unregisterReceiver(broadcastReceiver);
    }

    private void setHightlightandCommentary() {
        tvHightlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCommentary.setTextColor(getResources().getColor(R.color.gray));
                tvCommentary.setBackgroundColor(getResources().getColor(R.color.white));
                tvHightlight.setTextColor(getResources().getColor(R.color.white));
                tvHightlight.setBackgroundColor(getResources().getColor(R.color.red));
                hightlightAdapter = new HightlightAdapter(mContext, arrTimeline, idTeamA, idTeamB);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setReverseLayout(true);
                rcvTimeline.setLayoutManager(linearLayoutManager);
                rcvTimeline.setAdapter(hightlightAdapter);
                refreshData();

            }
        });
        tvCommentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHightlight.setTextColor(getResources().getColor(R.color.gray));
                tvHightlight.setBackgroundColor(getResources().getColor(R.color.white));
                tvCommentary.setTextColor(getResources().getColor(R.color.white));
                tvCommentary.setBackgroundColor(getResources().getColor(R.color.red));
                timelineAdapter = new TimelineAdapter(mContext, arrTimeline, idTeamA, idTeamB);
                rcvTimeline.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcvTimeline.setAdapter(timelineAdapter);
                refreshData();
            }
        });
    }
}
