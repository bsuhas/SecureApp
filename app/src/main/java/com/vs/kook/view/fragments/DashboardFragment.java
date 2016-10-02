package com.vs.kook.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vs.kook.R;
import com.vs.kook.view.custom.GaugeView;

import java.util.Random;

/**
 * Created by SUHAS on 10/2/2016.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private GaugeView mGaugeView1,mGaugeView2;
    private final Random RAND = new Random();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_layout, container, false);
        mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        mGaugeView1 = (GaugeView) view.findViewById(R.id.gauge_view1);
        mGaugeView2 = (GaugeView) view.findViewById(R.id.gauge_view2);
        mTimer.start();
    }


    private final CountDownTimer mTimer = new CountDownTimer(30000, 1000) {

        @Override
        public void onTick(final long millisUntilFinished) {
            mGaugeView1.setTargetValue(10);
            mGaugeView2.setTargetValue(80);
        }

        @Override
        public void onFinish() {
        }
    };


    @Override
    public void onClick(View view) {

    }
}
