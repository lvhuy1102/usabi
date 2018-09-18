package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.objects.Timeline;
import com.hcpt.multileagues.widget.textview.TextViewRobotoRegular;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private ArrayList<Timeline> arr = null;
    private Context mContext;
    private String idTeamA, idTeamB;

    public TimelineAdapter(Context context, ArrayList<Timeline> arr, String idTeamA, String idTeamB) {
        this.mContext = context;
        this.arr = arr;
        this.idTeamA = idTeamA;
        this.idTeamB = idTeamB;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_timeline_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Timeline item = arr.get(position);

        if (position == 0) {
            holder.imgTimeline.setImageResource(R.drawable.timeline_start);
        } else if (position == (arr.size() - 1)) {
            holder.imgTimeline.setImageResource(R.drawable.timeline_end);
        } else {
            holder.imgTimeline.setImageResource(R.drawable.timeline_middle);
        }
        if (item.getIdTeam().equals(idTeamA)) {
            //set event for team 1
            holder.imgEventTeam1.setVisibility(View.VISIBLE);
            holder.txtTimeTeam1.setVisibility(View.VISIBLE);
            holder.txtPlayerTeam1.setVisibility(View.VISIBLE);

            holder.imgEventTeam2.setVisibility(View.INVISIBLE);
            holder.txtTimeTeam2.setVisibility(View.INVISIBLE);
            holder.txtPlayerTeam2.setVisibility(View.INVISIBLE);

            holder.txtTimeTeam1.setText(item.getTime() + "'");
            holder.txtPlayerTeam1.setText(item.getPlayer());
            switch (item.getEvent()) {
                case 1:
                    holder.imgEventTeam1.setImageResource(R.mipmap.football);
                    break;
                case 2:
                    holder.imgEventTeam1.setImageResource(R.mipmap.red_card);
                    break;
                case 3:
                    holder.imgEventTeam1.setImageResource(R.mipmap.yellow);
                    break;
                case 4:
                    holder.imgEventTeam1.setImageResource(R.mipmap.ic_own_goal);
                    break;
                case 5:
                    holder.imgEventTeam1.setImageResource(R.mipmap.penalty);
                    break;
                case 6:
                    holder.imgEventTeam1.setImageResource(R.drawable.ic_in_teama);
                    break;
                case 7:
                    holder.imgEventTeam1.setImageResource(R.drawable.ic_out_teama);
                    break;
                default:
                    break;

            }


        } else {
            //set event for team 2
            holder.imgEventTeam2.setVisibility(View.VISIBLE);
            holder.txtTimeTeam2.setVisibility(View.VISIBLE);
            holder.txtPlayerTeam2.setVisibility(View.VISIBLE);

            holder.imgEventTeam1.setVisibility(View.INVISIBLE);
            holder.txtTimeTeam1.setVisibility(View.INVISIBLE);
            holder.txtPlayerTeam1.setVisibility(View.INVISIBLE);

            holder.txtTimeTeam2.setText(item.getTime() + "'");
            holder.txtPlayerTeam2.setText(item.getPlayer());
            switch (item.getEvent()) {
                case 1:
                    holder.imgEventTeam2.setImageResource(R.mipmap.football);
                    break;
                case 2:
                    holder.imgEventTeam2.setImageResource(R.mipmap.red_card);
                    break;
                case 3:
                    holder.imgEventTeam2.setImageResource(R.mipmap.yellow);
                    break;
                case 4:
                    holder.imgEventTeam2.setImageResource(R.mipmap.ic_own_goal);
                    break;
                case 5:
                    holder.imgEventTeam2.setImageResource(R.mipmap.penalty);
                    break;
                case 6:
                    holder.imgEventTeam2.setImageResource(R.drawable.ic_in_teamb);
                    break;
                case 7:
                    holder.imgEventTeam2.setImageResource(R.drawable.ic_out_teamb);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTimeline, imgEventTeam1, imgEventTeam2;
        private TextViewRobotoRegular txtTimeTeam1, txtTimeTeam2;
        private TextViewRobotoRegular txtPlayerTeam1, txtPlayerTeam2;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTimeline = (ImageView) itemView.findViewById(R.id.imgTimelineItem);
            imgEventTeam1 = (ImageView) itemView.findViewById(R.id.imgEventTeam1);
            imgEventTeam2 = (ImageView) itemView.findViewById(R.id.imgEventTeam2);

            txtTimeTeam1 = (TextViewRobotoRegular) itemView.findViewById(R.id.txtTimeTeam1);
            txtTimeTeam2 = (TextViewRobotoRegular) itemView.findViewById(R.id.txtTimeTeam2);
            txtPlayerTeam1 = (TextViewRobotoRegular) itemView.findViewById(R.id.txtPlayerTeam1);
            txtPlayerTeam2 = (TextViewRobotoRegular) itemView.findViewById(R.id.txtPlayerTeam2);

        }
    }
}