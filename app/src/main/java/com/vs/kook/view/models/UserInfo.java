package com.vs.kook.view.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 15/10/2016.
 */

public class UserInfo implements Serializable {

    @SerializedName("user_id")
    private String useId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("user_password")
    private String userPassword;

    @SerializedName("dob")
    private String dob;

    @SerializedName("device_name")
    private String deviceName;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("address")
    private String address;

    @SerializedName("gender")
    private String gender;

    @SerializedName("register_date")
    private String registerDate;

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
