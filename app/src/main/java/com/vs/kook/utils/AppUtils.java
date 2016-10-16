package com.vs.kook.utils;

import android.content.Context;
import android.location.LocationManager;
import android.telephony.TelephonyManager;

/**
 * Created by SUHAS on 15/10/2016.
 */

public class AppUtils {
    public static boolean isLocationFromNetworkEnabled(LocationManager locationManager) {
        // getting GPS status
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
