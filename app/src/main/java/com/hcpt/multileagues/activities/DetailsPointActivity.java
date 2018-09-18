package com.hcpt.multileagues.activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.DetailPointTabAdapter;
import com.hcpt.multileagues.adapters.HomeMainAdapter;

public class DetailsPointActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailPointTabAdapter adapter;
    private ImageView iv_menu;
    private TextView tvTitle;
    private TextView tvPurchasepoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_point);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }

        tvPurchasepoint = findViewById(R.id.tvPurchasepoint);
        tvPurchasepoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsPointActivity.this, PurcharseMorePointsActivity.class);
                startActivity(intent);
            }
        });
        iv_menu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tvTitle);
        iv_menu.setVisibility(View.VISIBLE);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("Detailed Point");
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new DetailPointTabAdapter(manager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
}
