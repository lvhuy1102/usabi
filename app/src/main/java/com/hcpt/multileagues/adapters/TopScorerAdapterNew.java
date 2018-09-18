package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hcpt.multileagues.R;
import com.hcpt.multileagues.objects.TopScorer;
import com.hcpt.multileagues.objects.TopScorerObj;
import com.hcpt.multileagues.widget.textview.TextViewRobotoRegular;

import java.util.ArrayList;

public class TopScorerAdapterNew extends RecyclerView.Adapter<TopScorerAdapterNew.ViewHolder> {
    private ArrayList<TopScorerObj> arr = null;
    private Context mContext;
    private int mRank = 0;

    public TopScorerAdapterNew(Context context, ArrayList<TopScorerObj> arr) {
        this.mContext = context;
        this.arr = arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_top_scorer_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopScorerObj item = arr.get(position);
        mRank = position + 1;
        holder.txtPlayerTopScoreItem.setSelected(true);
        holder.txtOrdinarilyTopScoreItem.setText(mRank + "");
        holder.txtPlayerTopScoreItem.setText(item.getmPlayer());
        holder.txtScoreTopScoreItem.setText(item.getmScore());
        if (mRank == 1 || mRank == 2 || mRank == 3) {
            holder.txtOrdinarilyTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.yeallow_color_huy));
            holder.txtPlayerTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.yeallow_color_huy));
            holder.txtTeamCodeTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.yeallow_color_huy));
            holder.txtScoreTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.yeallow_color_huy));
        } else {
            holder.txtOrdinarilyTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.textColorSecondary));
            holder.txtPlayerTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.textColorSecondary));
            holder.txtTeamCodeTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.textColorSecondary));
            holder.txtScoreTopScoreItem.setTextColor(mContext.getResources().getColor(R.color.textColorSecondary));
        }
//        if (item.getTeam().getTeamCode() != null) {
//            holder.txtTeamCodeTopScoreItem.setText(item.getTeam().getTeamCode());
//        }
//        if (item.getTeam() != null) {
//            Glide.with(mContext).load(item.getTeam().getLinkFlag()).error(R.drawable.flag_noname).into(holder.imgTeam);
//        }

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTeam;
        private TextViewRobotoRegular txtOrdinarilyTopScoreItem, txtTeamCodeTopScoreItem, txtPlayerTopScoreItem, txtScoreTopScoreItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTeam = (ImageView) itemView.findViewById(R.id.imgTeamTopScoreItem);

            txtOrdinarilyTopScoreItem = (TextViewRobotoRegular) itemView.findViewById(R.id.txtOrdinarilyTopScoreItem);
            txtTeamCodeTopScoreItem = (TextViewRobotoRegular) itemView.findViewById(R.id.txtTeamCodeTopScoreItem);
            txtPlayerTopScoreItem = (TextViewRobotoRegular) itemView.findViewById(R.id.txtPlayerTopScoreItem);
            txtScoreTopScoreItem = (TextViewRobotoRegular) itemView.findViewById(R.id.txtScoreTopScoreItem);

        }
    }
}
