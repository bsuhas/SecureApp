package com.vs.kook.view.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 16/10/2016.
 */

public class LoginReqModel {
    @SerializedName("email_id")
    private String emailId;

    @SerializedName("password")
    private String password;

    @SerializedName("device_id")
    private String deviceId;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
