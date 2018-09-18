package com.hcpt.multileagues.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.LeagueAdapter;
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

public class LeagueFragment extends Fragment {
    private RecyclerView mLvLeague;
    private LeagueAdapter mLeagueAdapter;
    public static LeagueFragment leagueFragment;
    private ProgressBar progressbar;

    public static LeagueFragment newInstance() {
        if (leagueFragment == null)
            leagueFragment = new LeagueFragment();
        return leagueFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.league_fragment, container, false);
        mLvLeague = view.findViewById(R.id.lv_league);
        progressbar = view.findViewById(R.id.progressbar);
        getLeagues();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.tvTitle);
        textView.setText("LEAGUE");
        return view;
    }

    private void getLeagues() {
        if (GlobalValue.arrLeague == null) {
            GlobalValue.arrLeague = new ArrayList<>();
        } else {
            GlobalValue.arrLeague.clear();
        }
        if (NetworkUtility.getInstance(getContext()).isNetworkAvailable()) {
            getLeaguesFromAPI();
        } else {
            getLeaguesFromDB();
        }
    }

    private void getLeaguesFromDB() {
        APIObj apiInfos = DatabaseUtility.getResuftFromApi(getActivity(), WebservicesConfigs.URL_GET_LEAGUE);
        if (apiInfos != null) {
            if (GlobalValue.arrLeague != null) {
                GlobalValue.arrLeague.clear();
            }

            String json = apiInfos.getmResult();
            ArrayList<LeagueObj> arr = ParserUtils.parserLeagues(json);
            GlobalValue.arrLeague.addAll(arr);
            setmLeagueAdapter();
        }
    }

    private void getLeaguesFromAPI() {
        ModelManager.getLeagues(getActivity(), false, new ModelManagerListener() {

            @Override
            public void onSuccess(Object object) {
                progressbar.setVisibility(View.GONE);
                if (GlobalValue.arrLeague != null) {
                    GlobalValue.arrLeague.clear();
                }

                String json = (String) object;
                ArrayList<LeagueObj> arr = ParserUtils.parserLeagues(json);
                progressbar.setVisibility(View.GONE);
                GlobalValue.arrLeague.addAll(arr);
                setmLeagueAdapter();

            }

            @Override
            public void onError() {
            }
        });
    }

    private void setmLeagueAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLvLeague.setLayoutManager(layoutManager);
        mLeagueAdapter = new LeagueAdapter(GlobalValue.arrLeague, getContext(), getActivity().getSupportFragmentManager());
        mLvLeague.setAdapter(mLeagueAdapter);
    }
}
