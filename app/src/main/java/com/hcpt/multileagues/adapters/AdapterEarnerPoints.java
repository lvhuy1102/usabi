package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hcpt.multileagues.R;

import java.util.ArrayList;

public class AdapterEarnerPoints extends RecyclerView.Adapter<AdapterEarnerPoints.ViewHolder> {
    private Context context;
    private ArrayList<String> stringArrayList;


    public AdapterEarnerPoints(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_points, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPoint.setTextColor(context.getResources().getColor(R.color.blue));
        holder.tvName.setText(stringArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPoint;
        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPoint = itemView.findViewById(R.id.tvPoint);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
}
