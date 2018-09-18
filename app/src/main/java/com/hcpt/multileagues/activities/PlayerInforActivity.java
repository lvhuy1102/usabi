package com.hcpt.multileagues.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcpt.multileagues.R;

public class PlayerInforActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView iv_menu;
    private Toolbar toolbar;
    private LinearLayout lnlToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_infor);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }
        tvTitle = findViewById(R.id.tvTitle);
        iv_menu = findViewById(R.id.iv_menu);
        toolbar = findViewById(R.id.toolbar);
        lnlToolbar = toolbar.findViewById(R.id.lnlToolbar);
        lnlToolbar.setBackgroundColor(getResources().getColor(R.color.gray));
        toolbar.setBackgroundColor(getResources().getColor(R.color.gray));
        tvTitle.setText("Player Infor");
        iv_menu.setVisibility(View.VISIBLE);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
