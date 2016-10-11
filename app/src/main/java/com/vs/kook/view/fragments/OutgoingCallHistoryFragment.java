package com.vs.kook.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vs.kook.R;
import com.vs.kook.utils.DialogUtils;
import com.vs.kook.view.adapters.OutgoingAdapter;
import com.vs.kook.view.interfaces.IDialogCallBack;
import com.vs.kook.view.models.CallHistoryModel;

import java.util.ArrayList;

/**
 * Created by SUHAS on 06/10/2016.
 */

@SuppressLint("ValidFragment")
public class OutgoingCallHistoryFragment extends Fragment {
   int color;
   private ArrayList<CallHistoryModel> modelCallHistoryList = new ArrayList<>();
    private Context mContext;

    @SuppressLint("ValidFragment")
    public OutgoingCallHistoryFragment(int color, ArrayList<CallHistoryModel> outgoingCallHistoryList) {
        for(int i= 0;i<outgoingCallHistoryList.size();i++){
            CallHistoryModel model = outgoingCallHistoryList.get(i);
            if(model.getType().equalsIgnoreCase("OUTGOING")){
                modelCallHistoryList.add(model);
            }
        }
//        modelCallHistoryList = outgoingCallHistoryList;
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

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvOutGoing.setLayoutManager(mLayoutManager);
        rvOutGoing.setItemAnimator(new DefaultItemAnimator());

        OutgoingAdapter adapter = new OutgoingAdapter(getContext(),modelCallHistoryList);
        rvOutGoing.setAdapter(adapter);
    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        try {
//            int position = OutgoingAdapter.getPosition();
//        } catch (Exception e) {
//            return super.onContextItemSelected(item);
//        }
//        if (item.getTitle() == mContext.getString(R.string.call_back)) {
//
//        } else if (item.getTitle() == mContext.getString(R.string.delete)) {
//            DialogUtils.showDeleteDialog(mContext, "", mContext.getString(R.string.err_msg_are_sure), new IDialogCallBack() {
//                @Override
//                public void getDialogCallBack(boolean result) {
//
//                }
//            });
//        } else {
//            return false;
//
//        }
//        return super.onContextItemSelected(item);
//    }

}

