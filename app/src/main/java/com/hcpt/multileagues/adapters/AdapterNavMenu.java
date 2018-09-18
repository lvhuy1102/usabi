package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hcpt.multileagues.R;
import com.hcpt.multileagues.objects.TabItem;

import java.util.ArrayList;

public class AdapterNavMenu extends RecyclerView.Adapter<AdapterNavMenu.ViewHolder> {
    private ArrayList<TabItem> tabItemArrayList;
    private ClickListener clickListener;
    private Context context;


    public AdapterNavMenu(ArrayList<TabItem> tabItemArrayList, ClickListener clickListener,Context context) {
        this.tabItemArrayList = tabItemArrayList;
        this.clickListener = clickListener;
        this.context=context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_drawer_league, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvFragment.setText(tabItemArrayList.get(position).getTitle());
        Glide.with(context).load(tabItemArrayList.get(position).getIcon()).into(holder.logo_league);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position, tabItemArrayList.get(position).getFragment());
            }
        });

    }

    @Override
    public int getItemCount() {
        return tabItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFragment;
        private ImageView logo_league;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFragment = itemView.findViewById(R.id.lbl_league);
            logo_league=itemView.findViewById(R.id.logo_league);
        }
    }

    public interface ClickListener {
        void onClick(int position, Fragment fragment);
    }
}
