package com.vs.kook.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.vs.kook.view.models.UserInfo;

/**
 * Created by SUHAS on 15/10/2016.
 */

public class UserSharedPreferences {

    public final String USER_ID = "userId";
    public final String FIRST_NAME = "first_name";
    public final String LAST_NAME = "last_name";
    public final String USER_EMAIL = "user_email";
    public final String USER_PASSWORD = "user_password";
    public final String DOB = "dob";
    public final String DEVICE_NAME = "device_name";
    public final String DEVICE_ID = "device_id";
    public final String ADDRESS = "address";
    public final String GENDER = "gender";


    private final String USER_PREFERENCE = "user_preference";

    private SharedPreferences mSharedPreferences;

    private static UserSharedPreferences mInstance;

    public static UserSharedPreferences getInstance(Context context) {
        if (mInstance == null)
            mInstance = new UserSharedPreferences(context);

        return mInstance;
    }

    private UserSharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void clearData() {
        mSharedPreferences.edit().clear().commit();
    }

    private void saveString(String key, String value) {
        if (mSharedPreferences == null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void saveInt(String key, int value) {
        if (mSharedPreferences == null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void saveBoolean(String key, boolean value) {
        if (mSharedPreferences == null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private String loadString(String key, String defaultValue) {
        String data = mSharedPreferences.getString(key, defaultValue);
        return data;
    }

    private int loadInt(String key, int defaultValue) {
        int data = mSharedPreferences.getInt(key, defaultValue);
        return data;
    }

    private boolean loadBoolean(String key, boolean defaultValue) {
        boolean data = mSharedPreferences.getBoolean(key, defaultValue);
        return data;
    }

    public void clearUserPreferences() {
        saveString(USER_ID, "");
        saveString(FIRST_NAME, "");
        saveString(LAST_NAME, "");
        saveString(USER_EMAIL, "");
        saveString(USER_PASSWORD, "");

        saveString(DOB, "");
        saveString(DEVICE_NAME, "");
        saveString(DEVICE_ID, "");
        saveString(ADDRESS, "");
        saveString(GENDER, "");
    }

    public void saveUserPreferences(UserInfo model) {
        clearUserPreferences();
        saveString(USER_ID, model.getUseId());
        saveString(FIRST_NAME, model.getFirstName());
        saveString(LAST_NAME, model.getLastName());
        saveString(USER_EMAIL, model.getUserEmail());
        saveString(USER_PASSWORD, model.getUserPassword());

        saveString(DOB, model.getDob());
        saveString(DEVICE_NAME, model.getDeviceName());
        saveString(DEVICE_ID, model.getDeviceId());
        saveString(ADDRESS, model.getAddress());
        saveString(GENDER, model.getGender());
    }

    public UserInfo getUserPreferences() {
        UserInfo model = new UserInfo();

        model.setUseId(loadString(USER_ID, ""));
        model.setFirstName(loadString(FIRST_NAME, ""));
        model.setLastName(loadString(LAST_NAME, ""));
        model.setUserEmail(loadString(USER_EMAIL, ""));
        model.setUserPassword(loadString(USER_PASSWORD, ""));

        model.setDob(loadString(DOB, ""));
        model.setDeviceName(loadString(DEVICE_NAME, ""));
        model.setDeviceId(loadString(DEVICE_ID, ""));
        model.setAddress(loadString(ADDRESS, ""));
        model.setGender(loadString(GENDER, ""));

        return model;
    }

    public boolean isUserLogin() {
        if (loadString(USER_ID, "").equalsIgnoreCase("")) {
            return false;
        } else {
            return true;
        }
    }
}
