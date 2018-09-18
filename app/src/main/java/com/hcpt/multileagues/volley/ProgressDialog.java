package com.hcpt.multileagues.volley;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.hcpt.multileagues.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(
                R.drawable.bg_black_transparent);
        setContentView(R.layout.layout_progress_dialog);

    }
}
