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
import com.vs.kook.view.models.CallHistoryModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by SUHAS on 06/10/2016.
 */

public class CallHistoryFragment extends Fragment {
    private Context mContext;
    private ArrayList<CallHistoryModel> mCallHistoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_history_fragment, container, false);
        mContext = getActivity();
        getData(view);
//        init(view);
        return view;
    }

    private void getData(View view) {
        mCallHistoryList = getCallHistory(mContext);
        init(view);
    }

    private void init(View view) {
        ((MainActivity) getActivity()).setTitle(mContext.getString(R.string.call_history));
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


    private  ArrayList<CallHistoryModel> getCallHistory(Context context) {
        DBHelper db = new DBHelper(context);
        ArrayList<CallHistoryModel> list = new ArrayList<>();
        Cursor c = db.getData();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                CallHistoryModel model = new CallHistoryModel();
                model.setNumber(c.getString(0));
                model.setDate(c.getString(1));
                model.setTime(c.getString(2));
                model.setDuration(c.getString(3));
                model.setType(c.getString(4));
                Log.e("Test",model.getType());
                list.add(model);
            } while (c.moveToNext());
        } else {
            Log.e("Call Details:", "No Incoming and Outgoing call history exists!!!");
        }
        return list;
    }


    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle=new Bundle();
        bundle.putString("CallHistoryModel", "From Activity");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new OutgoingCallHistoryFragment(ContextCompat.getColor(getActivity(),R.color.md_white_1000),mCallHistoryList), "Outgoing");
        adapter.addFrag(new IncomingCallHistoryFragment(ContextCompat.getColor(getActivity(),R.color.md_white_1000),mCallHistoryList), "Incoming");
        adapter.addFrag(new MissedCallHistoryFragment(ContextCompat.getColor(getActivity(),R.color.md_white_1000),mCallHistoryList), "Missed");
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
