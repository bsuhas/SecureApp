package com.vs.kook.view.database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;

import com.vs.kook.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class History extends ContentObserver {

    Context mContext;

    public History(Handler handler, Context context) {
        super(handler);
        mContext = context;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {

        super.onChange(selfChange);
        SharedPreferences sp = mContext.getSharedPreferences(AppConstants.SHARED_PREF, Activity.MODE_PRIVATE);
        String number = sp.getString("number", null);
        if (number != null) {
            getCallDetailsNow();
            sp.edit().putString("number", null).commit();
        }
    }

    private void getCallDetailsNow() {

        Cursor managedCursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration1 = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type1 = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date1 = managedCursor.getColumnIndex(CallLog.Calls.DATE);

        if (managedCursor.moveToFirst() == true) {
            String phNumber = managedCursor.getString(number);
            String callDuration = managedCursor.getString(duration1);

            String type = managedCursor.getString(type1);
            String date = managedCursor.getString(date1);

            String dir = null;
            int dircode = Integer.parseInt(type);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
                default:
                    dir = "MISSED";
                    break;
            }

            SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf_time = new SimpleDateFormat("h:mm a");
            // SimpleDateFormat sdf_dur = new SimpleDateFormat("KK:mm:ss");

            String dateString = sdf_date.format(new Date(Long.parseLong(date)));
            String timeString = sdf_time.format(new Date(Long.parseLong(date)));
            //  String duration_new=sdf_dur.format(new Date(Long.parseLong(callDuration)));

            DBHelper db = new DBHelper(mContext);
            db.insertCallHistoryData(phNumber, dateString, timeString, callDuration, dir);

        }

        managedCursor.close();
    }

}
