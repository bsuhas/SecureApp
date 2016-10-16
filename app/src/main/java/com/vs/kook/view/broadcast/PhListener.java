package com.vs.kook.view.broadcast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.vs.kook.utils.AppConstants;
import com.vs.kook.utils.Constants;
import com.vs.kook.view.database.DBHelper;
import com.vs.kook.view.database.History;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class PhListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {
        Bundle bundle = i.getExtras();

        if (bundle == null)
            return;

        //SMS
        String action = i.getAction();
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            // action for sms received
            SmsMessage[] messages = null;
            String strMessage = "";

            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                long timeMilisec = 0;
                String msgphonenumber = null;
                for (int j = 0; j < messages.length; j++) {

                    Log.e("inside SMsMessage", "" + j);
                    messages[j] = SmsMessage.createFromPdu((byte[]) pdus[j]);
                    msgphonenumber = "" + messages[j].getOriginatingAddress();
                    strMessage += messages[j].getMessageBody();
                    timeMilisec = messages[j].getTimestampMillis();
                }

                //Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                Log.d("Incoming_MSG_number", "" + msgphonenumber);

                SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf_time = new SimpleDateFormat("h:mm a");
                // SimpleDateFormat sdf_dur = new SimpleDateFormat("KK:mm:ss");

                String dateString = sdf_date.format(new Date(timeMilisec));
                String timeString = sdf_time.format(new Date(timeMilisec));

                Log.e("Info","SMS Date : "+dateString);
                Log.e("Info","SMS Time : "+timeString);
                //  String duration_new=sdf_dur.format(new Date(Long.parseLong(callDuration)));

                DBHelper db = new DBHelper(c);
                db.insertSMSHistoryData(msgphonenumber, dateString,timeString, strMessage,Constants.RECEIVED_SMS);
                db.close();
            }// if

        }


        //=============================

        SharedPreferences sp = c.getSharedPreferences(AppConstants.SHARED_PREF, Activity.MODE_PRIVATE);

        String s = bundle.getString(TelephonyManager.EXTRA_STATE);

        if (i.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String number = i.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            sp.edit().putString("number", number).commit();
            sp.edit().putString("state", s).commit();
        }
        if (s != null ) {
             if (s.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String number = bundle.getString("incoming_number");
                sp.edit().putString("number", number).commit();
                sp.edit().putString("state", s).commit();
            } else if (s.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                sp.edit().putString("state", s).commit();
            } else if (s.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                String state = sp.getString("state", null);
                if (!state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    sp.edit().putString("state", null).commit();
                    History h = new History(new Handler(), c);
                    c.getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, h);
                }
                sp.edit().putString("state", s).commit();
            }

        }

    }

}
