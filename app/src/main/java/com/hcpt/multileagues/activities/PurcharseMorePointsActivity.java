package com.hcpt.multileagues.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcpt.multileagues.R;

public class PurcharseMorePointsActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView iv_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purcharse_more_points);
        tvTitle = findViewById(R.id.tvTitle);
        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setVisibility(View.VISIBLE);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("Purchase more points");
    }
}
