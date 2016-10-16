package com.vs.kook.view.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

/**
 * Created by SUHAS on 14/10/2016.
 */

public class SMSSendService extends Service {
    static final Uri SMS_STATUS_URI = Uri.parse("content://sms");
    @Override
    public void onCreate() {
//        SmsContent content = new SmsContent(new Handler());
        // REGISTER ContetObserver
//        this.getContentResolver().
//                registerContentObserver(Uri.parse("content://sms/", true, SMSObserver));

//
        SMSObserver smsSentObserver = new SMSObserver(new Handler(), this);
        this.getContentResolver().registerContentObserver(SMS_STATUS_URI, true, smsSentObserver);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
