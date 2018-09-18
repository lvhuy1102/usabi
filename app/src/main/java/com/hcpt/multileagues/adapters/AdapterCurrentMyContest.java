package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hcpt.multileagues.R;

import java.util.ArrayList;

public class AdapterCurrentMyContest extends RecyclerView.Adapter<AdapterCurrentMyContest.ViewHolder> {
    private Context context;
    private ArrayList<String> stringArrayList;


    public AdapterCurrentMyContest(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_match_notheader, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvNameTeam1.setText(stringArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameTeam1;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNameTeam1 = itemView.findViewById(R.id.tvNameTeam1);
        }
    }
}
