package com.hcpt.multileagues.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.fragments.ScheduleFragment;
import com.hcpt.multileagues.objects.RoundObj;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class SpinnerRoundAdapter extends BaseAdapter {

    private ArrayList<RoundObj> mArrRound;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public SpinnerRoundAdapter(Activity activity, ArrayList<RoundObj> arrItem) {
        this.mArrRound = arrItem;
        this.mActivity = activity;
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.e("SpinnerRoundAdapter", String.valueOf(mArrRound.size()));
        return mArrRound.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mArrRound.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_spinner, null);
            holder.lblItem = (TextView) convertView.findViewById(R.id.lbl_item_spinner);
            holder.lblItem.setSelected(true);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        RoundObj roundObj = mArrRound.get(position);
        if (roundObj != null) {
            holder.lblItem.setText(roundObj.getName());

            if (roundObj.isCurrent()) {
                holder.lblItem.setTextColor(ContextCompat.getColor(mActivity, R.color.red));
            } else {
                holder.lblItem.setTextColor(ContextCompat.getColor(mActivity, R.color.black));
            }
        }

        return convertView;
    }

    class Holder {
        private TextView lblItem;
    }
}