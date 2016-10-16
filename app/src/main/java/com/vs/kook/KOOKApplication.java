package com.vs.kook;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by SUHAS on 15/10/2016.
 */

public class KOOKApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").build());
        MultiDex.install(this);
        Stetho.initializeWithDefaults(this);

//        LeakCanary.install(this);
    }

}
