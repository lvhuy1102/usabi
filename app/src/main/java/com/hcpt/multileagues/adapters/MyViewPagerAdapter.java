package com.hcpt.multileagues.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hcpt.multileagues.R;
import com.hcpt.multileagues.activities.LoginActivity;
import com.hcpt.multileagues.activities.MainActivity;
import com.hcpt.multileagues.objects.Team;

public class MyViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3
            , R.drawable.welcome4, R.drawable.welcome5, R.drawable.welcome6, R.drawable.welcome7};

    public MyViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_image_splash, null);
        TextView btnContinue = view.findViewById(R.id.btnContinue);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        Glide.with(context).load(images[position]).into(imageView);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
