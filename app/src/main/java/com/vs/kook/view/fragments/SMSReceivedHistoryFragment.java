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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vs.kook.R;
import com.vs.kook.utils.Constants;
import com.vs.kook.view.adapters.OutgoingAdapter;
import com.vs.kook.view.adapters.SMSAdapter;
import com.vs.kook.view.models.SMSHistoryModel;

import java.util.ArrayList;

/*
 * Created by SUHAS on 06/10/2016.
 */

@SuppressLint("ValidFragment")
public class SMSReceivedHistoryFragment extends Fragment {
    private ArrayList<SMSHistoryModel> modelSMSHistoryList = new ArrayList<>();
    private Context mContext;

    @SuppressLint("ValidFragment")
    public SMSReceivedHistoryFragment(ArrayList<SMSHistoryModel> outgoingCallHistoryList) {
        for(int i= 0;i<outgoingCallHistoryList.size();i++){
            SMSHistoryModel model = outgoingCallHistoryList.get(i);
            if(model.getType().equalsIgnoreCase(""+ Constants.RECEIVED_SMS)){
                modelSMSHistoryList.add(model);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.outgoing_call_history_fragment, container, false);
        mContext = getContext();
        init(view);
        return view;
    }

    private void init(View view) {
        RecyclerView rvOutGoing = (RecyclerView) view.findViewById(R.id.recycler_view);
        RelativeLayout rlEmpty = (RelativeLayout) view.findViewById(R.id.rl_empty);
        TextView txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvOutGoing.setLayoutManager(mLayoutManager);
        rvOutGoing.setItemAnimator(new DefaultItemAnimator());


        if (modelSMSHistoryList.size() > 0) {
            SMSAdapter adapter = new SMSAdapter(getContext(),modelSMSHistoryList);
            rvOutGoing.setAdapter(adapter);
            rlEmpty.setVisibility(View.GONE);
            rvOutGoing.setVisibility(View.VISIBLE);
        } else {
            rlEmpty.setVisibility(View.VISIBLE);
            rvOutGoing.setVisibility(View.GONE);
            txtEmptyView.setText(mContext.getResources().getString(R.string.oops_no_msg_history));
        }

    }


}

