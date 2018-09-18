package com.hcpt.multileagues.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hcpt.multileagues.R;

public class FragmentQuestions extends Fragment {
    private View view;
    private TextView tvTimeCountDown;
    private TextView btnSubmid;
    private ImageView imgTeam1, imgTeam2;
    private TextView tvNameTeam1, tvNameTeam2;
    private TextView tvQuestion, tvPointQuestion;
    private ProgressBar progressBar;
    private ConstraintLayout ctQuestion, ctSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_questions, container, false);
        btnSubmid = view.findViewById(R.id.btnSubmit);
        tvTimeCountDown = view.findViewById(R.id.timeCountDown);
        progressBar = view.findViewById(R.id.progressbar);
        ctQuestion = view.findViewById(R.id.ctQuestion);
        ctSubmit = view.findViewById(R.id.ctSubmit);
        setTimeCountDown();
        btnSubmid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctQuestion.setVisibility(View.GONE);
                ctSubmit.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void setTimeCountDown() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimeCountDown.setText(millisUntilFinished / 1000 + "s");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvTimeCountDown.setText("done!");
            }
        }.start();
    }

}
