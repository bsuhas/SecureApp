package com.vs.kook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.vs.kook.R;
import com.vs.kook.utils.DialogUtils;
import com.vs.kook.utils.Utils;
import com.vs.kook.view.fragments.CallHistoryFragment;
import com.vs.kook.view.fragments.CleanerFragment;
import com.vs.kook.view.fragments.DashboardFragment;
import com.vs.kook.view.fragments.UninstallFragment;
import com.vs.kook.view.network.LogoutNetworking;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SystemBarTintManager mTintManager;
    private Context mContext;
    public static final int START_NEXT_ACTIVITY = 100;
    public static final int EROOR = 101;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_NEXT_ACTIVITY:
                    Intent intent = new Intent(MainActivity.this, LoginScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case EROOR:
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;
        init();
        Bundle bundle = new Bundle();
        setFragment(new DashboardFragment(), bundle);
        setTitle(getString(R.string.dashboard));
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int colorCode = ContextCompat.getColor(mContext, R.color.colorPrimary);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);

            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(colorCode))));
            setTitle(R.string.app_name);
        }
        applyKitKatTranslucency(Color.parseColor("#" + Integer.toHexString(colorCode)));
        intiDrawer(toolbar);
    }

    private void intiDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void applyKitKatTranslucency(int color) {
        if (Utils.hasKitKat()) {
            if (mTintManager == null)
                mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);

        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }

    public void setFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fl_container, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    public void setTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                DialogUtils.showYesNoLogoutConfirmDialogAndFinishActivity(this, "", "Do you want to close the app?");
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                setTitle(getString(R.string.dashboard));
                setFragment(new DashboardFragment(), bundle);
                break;
            case R.id.nav_cache:
                setTitle(getString(R.string.cache_cleaner));
                setFragment(new CleanerFragment(), bundle);
                break;
            case R.id.nav_history:
                setTitle(getString(R.string.call_history));
                setFragment(new CallHistoryFragment(), bundle);
                break;
            case R.id.nav_uninstall:
                setTitle(getString(R.string.uninstall_apps));
                setFragment(new UninstallFragment(), bundle);
                break;
            case R.id.nav_logout:
                 LogoutNetworking logoutNetworking = new LogoutNetworking(true,this,handler);
                 logoutNetworking.makeRequestAndInsert(null);
            default:
                setTitle(getString(R.string.dashboard));
                setFragment(new DashboardFragment(), bundle);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
