package com.vs.kook.view.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.vs.kook.R;
import com.vs.kook.view.activity.MainActivity;
import com.vs.kook.view.adapters.AppsUninstallListAdapter;
import com.vs.kook.view.models.AppsListItem;
import com.vs.kook.view.services.CleanerService;
import com.vs.kook.view.widget.RecyclerView;
import com.vs.kook.view.widget.UninstallDividerDecoration;

import java.util.List;

public class UninstallFragment extends Fragment implements CleanerService.OnActionListener {

    private static final int REQUEST_STORAGE = 0;

    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int UNINSTALL_REQUEST_CODE = 1 ;

    private CleanerService mCleanerService;
    private AppsUninstallListAdapter mAppsUninstallListAdapter;
    private TextView mEmptyView;
    private SharedPreferences mSharedPreferences;
    private ProgressDialog mProgressDialog;
    private View mProgressBar;
    private TextView mProgressBarText;
    private LinearLayoutManager mLayoutManager;
    private Menu mOptionsMenu;

    private boolean mAlreadyScanned = false;
    private boolean mAlreadyCleaned = false;
    private String mSearchQuery;

    private String mSortByKey;
    private String mCleanOnAppStartupKey;
    private String mExitAfterCleanKey;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCleanerService = ((CleanerService.CleanerServiceBinder) service).getService();
            mCleanerService.setOnActionListener(UninstallFragment.this);

            if (!mCleanerService.isCleaning() && !mCleanerService.isScanning()) {
                if (mSharedPreferences.getBoolean(mCleanOnAppStartupKey, false) &&
                        !mAlreadyCleaned) {
                    mAlreadyCleaned = true;

                    cleanCache();
                } else if (!mAlreadyScanned) {
                    mCleanerService.scanCache();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCleanerService.setOnActionListener(null);
            mCleanerService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        mCleanOnAppStartupKey = getString(R.string.clean_on_app_startup_key);
        mExitAfterCleanKey = getString(R.string.exit_after_clean_key);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mAppsUninstallListAdapter = new AppsUninstallListAdapter();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("");
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });

        getActivity().getApplication().bindService(new Intent(getActivity(), CleanerService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cleaner_fragment, container, false);

        mEmptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mLayoutManager = new LinearLayoutManager(getActivity());

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAppsUninstallListAdapter);
        recyclerView.setEmptyView(mEmptyView);
        recyclerView.addItemDecoration(new UninstallDividerDecoration(getActivity()));

        mProgressBar = rootView.findViewById(R.id.progressBar);
        mProgressBarText = (TextView) rootView.findViewById(R.id.progressBarText);
        ((MainActivity) getActivity()).setTitle(getActivity().getString(R.string.uninstall_apps));
        return rootView;
    }

    @Override
    public void onResume() {
        if (mCleanerService != null) {
            if (mCleanerService.isScanning() && !isProgressBarVisible()) {
                showProgressBar(true);
            } else if (!mCleanerService.isScanning() && isProgressBarVisible()) {
                showProgressBar(false);
            }

            if (mCleanerService.isCleaning() && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }

        super.onResume();
    }

    @Override
    public void onPause() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        getActivity().getApplication().unbindService(mServiceConnection);

        super.onDestroy();
    }
    private AppsUninstallListAdapter.SortBy getSortBy() {
        try {
            return AppsUninstallListAdapter.SortBy.valueOf(mSharedPreferences.getString(mSortByKey,
                    AppsUninstallListAdapter.SortBy.APP_NAME.toString()));
        } catch (ClassCastException e) {
            return AppsUninstallListAdapter.SortBy.APP_NAME;
        }
    }
    private boolean isProgressBarVisible() {
        return mProgressBar.getVisibility() == View.VISIBLE;
    }

    private void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.startAnimation(AnimationUtils.loadAnimation(
                    getActivity(), android.R.anim.fade_out));
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void showStorageRationale() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle(R.string.rationale_title);
        dialog.setMessage(getString(R.string.rationale_storage));
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.show();
    }

    private void cleanCache() {
        if (!CleanerService.canCleanExternalCache(getActivity())) {
            if (shouldShowRequestPermissionRationale(PERMISSIONS_STORAGE[0])) {
                showStorageRationale();
            } else {
                requestPermissions(PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        } else {
            mCleanerService.cleanCache();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCleanerService.cleanCache();
            } else {
                showStorageRationale();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onScanStarted(Context context) {
        if (isAdded()) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

            mProgressBarText.setText(R.string.scanning);
            showProgressBar(true);
        }
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max) {
        if (isAdded()) {
            mProgressBarText.setText(getString(R.string.scanning_m_of_n, current, max));
        }
    }

    @Override
    public void onScanCompleted(Context context, List<AppsListItem> apps) {
        mAppsUninstallListAdapter.setItems(getActivity(), apps, getSortBy(), mSearchQuery);

        if (isAdded()) {
            showProgressBar(false);
        }
        mAlreadyScanned = true;
    }

    @Override
    public void onCleanStarted(Context context) {
        if (isAdded()) {
            if (isProgressBarVisible()) {
                showProgressBar(false);
            }

            if (!getActivity().isFinishing()) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void onCleanCompleted(Context context, boolean succeeded) {
        if (succeeded) {
            mAppsUninstallListAdapter.trashItems();
        }

        if (isAdded()) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        Toast.makeText(context, succeeded ? R.string.cleaned : R.string.toast_could_not_clean,
                Toast.LENGTH_LONG).show();

        if (succeeded && getActivity() != null && !mAlreadyCleaned &&
                mSharedPreferences.getBoolean(mExitAfterCleanKey, false)) {
            getActivity().finish();
        }
    }
}
