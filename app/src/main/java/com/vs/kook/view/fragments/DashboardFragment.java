package com.vs.kook.view.fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.kook.R;
import com.vs.kook.view.activity.MainActivity;
import com.vs.kook.view.custom.GaugeView;

import java.util.Random;

/**
 * Created by SUHAS on 10/2/2016.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private int percentAvail;
    private Context mContext;
    private GaugeView mGaugeView1, mGaugeView2;
//    private final Random RAND = new Random();
    private CardView cvCacheCleaner, cvCallHistory;
    private CardView cvUninstall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_layout, container, false);
        mContext = getActivity();
        isRamFree(mContext);
        init(view);
        ((MainActivity) getActivity()).setTitle(getActivity().getString(R.string.dashboard));
        return view;
    }


    public void isRamFree(Context context) {
        if (Build.VERSION.SDK_INT > 15) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            double availableMegs = mi.availMem / 1048576L;
            //Percentage can be calculated for API 16+
            percentAvail = (int) (mi.availMem * 100 / mi.totalMem);
            Log.e("RAM:", "availableMegs:" + availableMegs + "\t percentAvail:" + percentAvail);
        }

        //TODO storage
        /*StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double actual = (double) stat.getBlockCount();
        actual = actual/1073741824;
        System.out.println("Megs : actual" + actual);

        double sdAvailSize = (double) stat.getAvailableBlocks() * (double) stat.getBlockSize();
        //One binary gigabyte equals 1,073,741,824 bytes.
        double gigaAvailable = sdAvailSize / 1073741824;
        System.out.println("Megs : Block" + gigaAvailable);

        double per = actual-gigaAvailable;
        System.out.println("Megs : per" + per);*/
    }

    private void init(View view) {
        mGaugeView1 = (GaugeView) view.findViewById(R.id.gauge_view1);
        mGaugeView2 = (GaugeView) view.findViewById(R.id.gauge_view2);
        cvCacheCleaner = (CardView) view.findViewById(R.id.cv_cache_cleaner);
        cvCallHistory = (CardView) view.findViewById(R.id.cv_contact_msg_history);
        cvUninstall = (CardView) view.findViewById(R.id.cv_uninstall);

        cvUninstall.setOnClickListener(this);
        cvCacheCleaner.setOnClickListener(this);
        cvCallHistory.setOnClickListener(this);
        mTimer.start();
    }


    private final CountDownTimer mTimer = new CountDownTimer(30000, 1000) {

        @Override
        public void onTick(final long millisUntilFinished) {
            //Storage Usage
            mGaugeView1.setTargetValue(10);

            //RAM Usage
            mGaugeView2.setTargetValue(100 - percentAvail);
        }

        @Override
        public void onFinish() {
        }
    };


    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.cv_cache_cleaner:
                ((MainActivity) getActivity()).setFragment(new CleanerFragment(), bundle);
                break;
            case R.id.cv_contact_msg_history:
                ((MainActivity) getActivity()).setFragment(new CallHistoryFragment(), bundle);
                break;
            case R.id.cv_uninstall:
                ((MainActivity) getActivity()).setFragment(new UninstallFragment(), bundle);
                break;
        }

    }


}
