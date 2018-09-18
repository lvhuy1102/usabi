package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.objects.TabItem;

import java.util.ArrayList;

public class AdapterLeaderBoard extends RecyclerView.Adapter<AdapterLeaderBoard.ViewHolder> {
    private ArrayList<String> stringArrayList;
    private Context context;

    public AdapterLeaderBoard(ArrayList<String> stringArrayList, Context context) {
        this.stringArrayList = stringArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int stt = position + 1;
        holder.tvName.setText(stringArrayList.get(position));
        holder.tvSTT.setText(String.valueOf(stt));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvSTT, tvName;
        private TextView tvCoin;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvSTT = itemView.findViewById(R.id.tvSTT);
            tvName = itemView.findViewById(R.id.tvName);
            tvCoin = itemView.findViewById(R.id.tvCoin);
        }
    }
}
