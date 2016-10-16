package com.vs.kook.view.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.kook.R;
import com.vs.kook.view.activity.MainActivity;
import com.vs.kook.view.database.DBHelper;
import com.vs.kook.view.models.SMSHistoryModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by SUHAS on 06/10/2016.
 */

public class SMSHistoryFragment extends Fragment {
    private Context mContext;
    private ArrayList<SMSHistoryModel> mSmsHistoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_history_fragment, container, false);
        mContext = getActivity();
        getData(view);
//        init(view);
        return view;
    }

    private void getData(View view) {
        DBHelper db = new DBHelper(mContext);
        mSmsHistoryList = db.getSMSHistoryData();
        init(view);
    }

    private void init(View view) {
        ((MainActivity) getActivity()).setTitle(mContext.getString(R.string.sms_history));
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle=new Bundle();
        bundle.putString("SMSHistoryModel", "From Activity");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new SMSReceivedHistoryFragment(mSmsHistoryList), "Received Messages");
        adapter.addFrag(new SMSSentHistoryFragment(mSmsHistoryList), "Sent Messages");
        viewPager.setAdapter(adapter);
    }


    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
