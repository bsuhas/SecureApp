package com.vs.kook.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.kook.R;
import com.vs.kook.view.adapters.OutgoingAdapter;
import com.vs.kook.view.models.CallHistoryModel;

import java.util.ArrayList;

/**
 * Created by SUHAS on 06/10/2016.
 */

@SuppressLint("ValidFragment")
public class MissedCallHistoryFragment extends Fragment {
    int color;
    ArrayList<CallHistoryModel> modelCallHistoryList = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public MissedCallHistoryFragment(int color, ArrayList<CallHistoryModel> outgoingCallHistoryList) {
        for(int i= 0;i<outgoingCallHistoryList.size();i++){
            CallHistoryModel model = outgoingCallHistoryList.get(i);
            if(model.getType().equalsIgnoreCase("MISSED")){
                modelCallHistoryList.add(model);
            }
        }
//        modelCallHistoryList = outgoingCallHistoryList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.outgoing_call_history_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        RecyclerView rvOutGoing = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvOutGoing.setLayoutManager(mLayoutManager);
        rvOutGoing.setItemAnimator(new DefaultItemAnimator());

        OutgoingAdapter adapter = new OutgoingAdapter(getContext(),modelCallHistoryList);
        rvOutGoing.setAdapter(adapter);
    }


}

