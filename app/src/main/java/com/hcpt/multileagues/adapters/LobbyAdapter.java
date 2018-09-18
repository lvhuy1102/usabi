package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.activities.MatchDetailActivity;
import com.hcpt.multileagues.activities.MatchDetailNotPlayed;
import com.hcpt.multileagues.configs.JsonConfigs;
import com.hcpt.multileagues.objects.MatchObj;
import com.hcpt.multileagues.utilities.DateTimeUtility;
import com.hcpt.multileagues.utilities.Debug;


import java.util.ArrayList;
import java.util.Locale;

public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MatchObj> arrMatch;


    public LobbyAdapter(Context context, ArrayList<MatchObj> arrMatch) {
        this.context = context;
        this.arrMatch = arrMatch;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_match_lobby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MatchObj m = arrMatch.get(position);
        if (m != null) {
            String scoreHome = "?";
            String scoreAway = "?";
            String strTime = "";
            // Get scores.
            switch (m.getmMatchStatus()) {
                case JsonConfigs.MATCH_STATUS_ACTIVE:
                    holder.tvNameTeam1.setText(m.getmHomeName());
                    holder.tvNameTeam2.setText(m.getmAwayName());
                    holder.tvTime.setText(m.getmMinute());
                    holder.lblHomeScore.setText(m.getmHomeScore());
                    holder.lblAwayScore.setText(m.getmAwayScore());
                    holder.tvDate.setText(DateTimeUtility.convertTimeStampToDate(m.getmTime(), "MMM dd HH:mm"));
                    holder.tvHeader.setText("PREMI LEAGUE");
                    break;
                case JsonConfigs.MATCH_STATUS_FINISHED:
                    holder.tvNameTeam1.setText(m.getmHomeName());
                    holder.tvNameTeam2.setText(m.getmAwayName());
                    holder.tvTime.setText("FINISH");
                    holder.lblHomeScore.setText(m.getmHomeScore());
                    holder.lblAwayScore.setText(m.getmAwayScore());
                    holder.tvDate.setText(DateTimeUtility.convertTimeStampToDate(m.getmTime(), "dd - MMM"));
                    holder.tvHeader.setText("PREMI LEAGUE");
                    break;
                case JsonConfigs.MATCH_STATUS_NOT_STARTED:
                    if (m.getmHomeName().toLowerCase(Locale.US).equals(null)) {
                        holder.tvNameTeam1.setText("");
                    } else {
                        Debug.e(m.getmHomeName() + "name k co");
                        holder.tvNameTeam1.setText(m.getmHomeName());
                    }
                    if (m.getmHomeName().toLowerCase(Locale.US).equals(null)) {
                        holder.tvNameTeam2.setText("");
                    } else {
                        Debug.e(m.getmAwayName() + "name k co");
                        holder.tvNameTeam2.setText(m.getmAwayName());
                    }
                    holder.tvTime.setText("02:45 PM");
                    holder.lblHomeScore.setText(scoreHome);
                    holder.lblAwayScore.setText(scoreAway);
                    holder.imgNotification.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // bắt sự kiện với thông báo notification

                        }
                    });
                    holder.tvDate.setText(DateTimeUtility.convertTimeStampToDate(m.getmTime(), "MMM dd HH:mm"));
                    holder.tvHeader.setText("PREMI LEAGUE");

                    break;

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MatchDetailActivity.currentMatch = m;
                    MatchDetailNotPlayed.currentMatch = m;

                    // Check status to set screen.
                    if (m.getmMatchStatus().equals(JsonConfigs.MATCH_STATUS_NOT_STARTED)) {
//                        context.startActivity(new Intent(context, MatchDetailNotPlayed.class));
                        context.startActivity(new Intent(context, MatchDetailActivity.class));
                    } else {
                        context.startActivity(new Intent(context, MatchDetailActivity.class));
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return arrMatch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTeam1, imgTeam2, imgNotification;
        private TextView tvDate, tvTime, tvNameTeam1, tvNameTeam2;
        private TextView lblHomeScore, lblAwayScore;
        private TextView tvHeader;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTeam1 = itemView.findViewById(R.id.imgTeam1);
            imgTeam2 = itemView.findViewById(R.id.imgTeam2);
            imgNotification = itemView.findViewById(R.id.imgNotification);
            tvDate = itemView.findViewById(R.id.tvDateMatch);
            tvTime = itemView.findViewById(R.id.tvTimeMatch);
            lblHomeScore = itemView.findViewById(R.id.lbl_home_score);
            lblAwayScore = itemView.findViewById(R.id.lbl_away_score);
            tvNameTeam1 = itemView.findViewById(R.id.tvNameTeam1);
            tvNameTeam2 = itemView.findViewById(R.id.tvNameTeam2);
            tvHeader = itemView.findViewById(R.id.tvHeader);
        }
    }

}