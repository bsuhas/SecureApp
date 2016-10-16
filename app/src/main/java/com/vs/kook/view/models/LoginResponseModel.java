package com.vs.kook.view.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 15/10/2016.
 */

public class LoginResponseModel implements Serializable {
    @SerializedName("error_code")
    private int error_code;

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("user_data_arr")
    private UserInfo userInfo;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
