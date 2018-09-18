package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.objects.Timeline;

import java.util.ArrayList;

public class HightlightAdapter extends RecyclerView.Adapter<HightlightAdapter.ViewHolder> {
    private ArrayList<Timeline> arr = null;
    private Context mContext;
    private String idTeamA, idTeamB;

    public HightlightAdapter(Context context, ArrayList<Timeline> arr, String idTeamA, String idTeamB) {
        this.mContext = context;
        this.arr = arr;
        this.idTeamA = idTeamA;
        this.idTeamB = idTeamB;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_timeline_hightlight, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Timeline item = arr.get(position);
        holder.tvTime.setText(arr.get(position).getTime());
//        holder.txtTimeTeam1.setText(item.getPlayer());

        switch (item.getEvent()) {
            case 1:
                holder.imgEvent.setImageResource(R.mipmap.football);
                break;
            case 2:
                holder.imgEvent.setImageResource(R.mipmap.red_card);
                break;
            case 3:
                holder.imgEvent.setImageResource(R.mipmap.yellow);
                break;
            case 4:
                holder.imgEvent.setImageResource(R.mipmap.ic_own_goal);
                break;
            case 5:
                holder.imgEvent.setImageResource(R.mipmap.penalty);
                break;
            case 6:
                holder.imgEvent.setImageResource(R.drawable.ic_in_teama);
                break;
            case 7:
                holder.imgEvent.setImageResource(R.drawable.ic_out_teama);
                break;
            default:
                break;

        }


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime, txtTimeTeam1;
        private ImageView imgLogo, imgEvent;
        private LinearLayout lnlLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            txtTimeTeam1 = itemView.findViewById(R.id.txtTimeTeam1);
            imgLogo = itemView.findViewById(R.id.imglogo);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            lnlLayout = itemView.findViewById(R.id.lnlLayout);


        }
    }
}
