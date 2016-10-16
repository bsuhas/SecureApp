package com.vs.kook.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.kook.R;
import com.vs.kook.view.activity.MainActivity;

/**
 * Created by SUHAS on 13/10/2016.
 */

public class FeedbackFragment extends Fragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment_layout, container, false);
        mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        ((MainActivity) getActivity()).setTitle(mContext.getString(R.string.feedback));
    }
}
