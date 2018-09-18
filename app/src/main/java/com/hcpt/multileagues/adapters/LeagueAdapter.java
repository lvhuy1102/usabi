package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hcpt.multileagues.R;
import com.hcpt.multileagues.configs.GlobalValue;
import com.hcpt.multileagues.fragments.FragmentTabLeague;
import com.hcpt.multileagues.objects.LeagueObj;
import com.hcpt.multileagues.utilities.Debug;

import java.util.ArrayList;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder> {
    private ArrayList<LeagueObj> mArrLeague;
    private Context context;
    private FragmentManager fragmentManager;

    public LeagueAdapter(ArrayList<LeagueObj> mArrLeague, Context context, FragmentManager fragmentManager) {
        this.mArrLeague = mArrLeague;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_drawer_league, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.lblName.setText(mArrLeague.get(position).getmName());
        Glide.with(context).load(mArrLeague.get(position).getmLogo()).into(holder.logoLeague);
        // Show/hide separate bar
        holder.separateBar.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalValue.leagueId = mArrLeague.get(position).getmId();
                fragmentManager.beginTransaction().replace(R.id.content_main, new FragmentTabLeague()).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        Debug.e(String.valueOf(mArrLeague.size()));
        return mArrLeague.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoLeague;
        private TextView lblName;
        private View separateBar;
        private ConstraintLayout ctslayout;

        public ViewHolder(View itemView) {
            super(itemView);
            logoLeague = itemView.findViewById(R.id.logo_league);
            lblName = itemView.findViewById(R.id.lbl_league);
            separateBar = itemView.findViewById(R.id.vw_separate);
            ctslayout = itemView.findViewById(R.id.ctslayout);
        }
    }
}
