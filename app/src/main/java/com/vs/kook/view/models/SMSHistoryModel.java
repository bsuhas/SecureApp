package com.vs.kook.view.models;

import java.io.Serializable;

/**
 * Created by SUHAS on 14/10/2016.
 */

public class SMSHistoryModel implements Serializable {
    private String number;
    private String date;
    private String time;
    private String type;
    private String body;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
