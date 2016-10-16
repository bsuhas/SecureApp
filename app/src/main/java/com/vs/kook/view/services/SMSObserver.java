package com.vs.kook.view.services;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.vs.kook.utils.Constants;
import com.vs.kook.view.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SUHAS on 14/10/2016.
 */

public class SMSObserver extends ContentObserver {

    private Context mContext;

    private String contactId = "", contactName = "";
    private String smsBodyStr = "", phoneNoStr = "";
    private long smsDatTime = System.currentTimeMillis();
    static final Uri SMS_STATUS_URI = Uri.parse("content://sms");

    public SMSObserver(Handler handler, Context ctx) {
        super(handler);
        mContext = ctx;
    }

    public boolean deliverSelfNotifications() {
        return true;
    }

    public void onChange(boolean selfChange) {
        try{
            Log.e("Info","Notification on SMS observer");
            Cursor sms_sent_cursor = mContext.getContentResolver().query(SMS_STATUS_URI, null, null, null, null);
            if (sms_sent_cursor != null) {
                if (sms_sent_cursor.moveToFirst()) {
                    String protocol = sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("protocol"));

                    if(protocol == null){
                       int type = sms_sent_cursor.getInt(sms_sent_cursor.getColumnIndex("type"));
                        if(type == 2){
                            smsBodyStr = sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("body")).trim();
                            phoneNoStr = sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("address")).trim();
                            smsDatTime = sms_sent_cursor.getLong(sms_sent_cursor.getColumnIndex("date"));

                            SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat sdf_time = new SimpleDateFormat("h:mm a");

                            String dateString = sdf_date.format(new Date(smsDatTime));
                            String timeString = sdf_time.format(new Date(smsDatTime));

                            Log.e("Info","SMS Date : "+dateString);
                            Log.e("Info","SMS Time : "+timeString);

                            DBHelper db = new DBHelper(mContext);
                            db.insertSMSHistoryData(phoneNoStr,dateString, timeString, smsBodyStr,Constants.SENT_SMS);
                            db.close();
                        }
                    }
                }
            }
            else
                Log.e("Info","Send Cursor is Empty");
            sms_sent_cursor.close();
        }
        catch(Exception sggh){
            Log.e("Error", "Error on onChange : "+sggh.toString());
        }
        super.onChange(selfChange);
    }//fn onChange

}//End of class SmsObserve


