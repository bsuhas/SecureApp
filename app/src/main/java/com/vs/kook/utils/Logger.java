package com.vs.kook.utils;

import android.util.Log;

/**
 * Created by SUHAS on 10/2/2016.
 */

public class Logger {

    private static final  String TAG = "SNL";

    private static final boolean DEBUG = true;

    /**
     * I.
     *
     * @param msgString
     *            the msg string
     */
    public static void i(String msgString){
        if (DEBUG) {
            Log.i(TAG,msgString);
        }
    }

    /**
     * D.
     *
     * @param msgString
     *            the msg string
     */
    public static void d(String msgString){
        if (DEBUG) {
            Log.d(TAG,msgString);
        }
    }

    /**
     * E.
     *
     * @param msgString
     *            the msg string
     */
    public static void e(String msgString){
        if (DEBUG) {
            Log.e(TAG,msgString);
        }
    }

    /**
     * W.
     *
     * @param msgString
     *            the msg string
     */
    public static void w(String msgString){
        if (DEBUG) {
            Log.w(TAG,msgString);
        }
    }

    /**
     * V.
     *
     * @param msgString
     *            the msg string
     */
    public static void v(String msgString){
        if (DEBUG) {
            Log.v(TAG,msgString);
        }
    }

}
