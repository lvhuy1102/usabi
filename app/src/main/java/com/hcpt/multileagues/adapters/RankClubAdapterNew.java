package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hcpt.multileagues.R;
import com.hcpt.multileagues.activities.ClubDetailActivity;
import com.hcpt.multileagues.configs.GlobalValue;
import com.hcpt.multileagues.objects.RankClubsObj;

import java.util.ArrayList;

public class RankClubAdapterNew extends RecyclerView.Adapter<RankClubAdapterNew.ViewHolder> {
    private Context context;
    private ArrayList<RankClubsObj> mArrRankClubs;
    private RankClubsObj mRankClubs;
    private int mRank = 0;

    public RankClubAdapterNew(Context context, ArrayList<RankClubsObj> mArrRankClubs) {
        this.context = context;
        this.mArrRankClubs = mArrRankClubs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rank_clubs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mRankClubs = mArrRankClubs.get(position);
        mRank = position + 1;
        if (mRankClubs != null && mArrRankClubs.size() >= 4) {
            if (mRank == 1 || mRank == 2 || mRank == 3 || mRank == 4) {
                holder.lblRank.setTextColor(context.getResources().getColor(R.color.red));
                holder.lblNameClubs.setTextColor(context.getResources().getColor(R.color.red));
                holder.lblP.setTextColor(context.getResources().getColor(R.color.text_color_blue));
                holder.lblW.setTextColor(context.getResources().getColor(R.color.text_color_blue));
                holder.lblD.setTextColor(context.getResources().getColor(R.color.text_color_blue));
                holder.lblL.setTextColor(context.getResources().getColor(R.color.text_color_blue));
                holder.lblGD.setTextColor(context.getResources().getColor(R.color.text_color_blue));
                holder.lblPTS.setTextColor(context.getResources().getColor(R.color.text_color_blue));
            } else {
                holder.lblRank.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblNameClubs.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblP.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblW.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblD.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblL.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblGD.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                holder.lblPTS.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
            }
            holder.lblRank.setText(mRank + "");
            holder.lblNameClubs.setText(mRankClubs.getmNameClubs());
            Glide.with(context).load(R.drawable.ic_bell_huy).into(holder.imgLogo);
            holder.lblP.setText(mRankClubs.getmP());
            holder.lblW.setText(mRankClubs.getmW());
            holder.lblD.setText(mRankClubs.getmD());
            holder.lblL.setText(mRankClubs.getmL());
            holder.lblGD.setText(mRankClubs.getmGD());
            holder.lblPTS.setText(mRankClubs.getmPTS());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ClubDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mArrRankClubs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lblRank, lblNameClubs, lblP, lblW, lblD, lblL, lblGD, lblPTS;
        private ImageView imgLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            lblRank = (TextView) itemView.findViewById(R.id.lbl_rank_clubs);
            imgLogo = (ImageView) itemView.findViewById(R.id.img_logoClub);
            lblNameClubs = (TextView) itemView.findViewById(R.id.lbl_name_clubs);
            lblP = (TextView) itemView.findViewById(R.id.lbl_value_p);
            lblW = (TextView) itemView.findViewById(R.id.lbl_value_w);
            lblD = (TextView) itemView.findViewById(R.id.lbl_value_d);
            lblL = (TextView) itemView.findViewById(R.id.lbl_value_l);
            lblGD = (TextView) itemView.findViewById(R.id.lbl_value_gd);
            lblPTS = (TextView) itemView.findViewById(R.id.lbl_value_pts);
        }
    }
}
