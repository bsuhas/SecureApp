package com.vs.kook.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vs.kook.R;
import com.vs.kook.view.adapters.OutgoingAdapter;
import com.vs.kook.view.models.CallHistoryModel;

import java.util.ArrayList;

/*
 * Created by SUHAS on 06/10/2016.
 */

@SuppressLint("ValidFragment")
public class ContactBackupFragment extends Fragment {
   private ArrayList<CallHistoryModel> modelCallHistoryList = new ArrayList<>();
    private Context mContext;

    @SuppressLint("ValidFragment")
    public ContactBackupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_backup__fragment, container, false);
        mContext = getContext();
        init(view);
        return view;
    }

    private void init(View view) {
        ListView rvOutGoing = (ListView) view.findViewById(R.id.lv_contact_view);
        RelativeLayout rlEmpty = (RelativeLayout) view.findViewById(R.id.rl_empty);
        TextView txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        rvOutGoing.setLayoutManager(mLayoutManager);
//        rvOutGoing.setItemAnimator(new DefaultItemAnimator());

        if (modelCallHistoryList.size() > 0) {
//            OutgoingAdapter adapter = new OutgoingAdapter(getContext(), modelCallHistoryList);
//            rvOutGoing.setAdapter(adapter);
            rlEmpty.setVisibility(View.GONE);
            rvOutGoing.setVisibility(View.VISIBLE);
        } else {
            rlEmpty.setVisibility(View.VISIBLE);
            rvOutGoing.setVisibility(View.GONE);
            txtEmptyView.setText(mContext.getResources().getString(R.string.oops_no_call_history));
        }
    }


}

