package com.example.ui.achatapplication;

public class Message {
    private int mid;
    private String userid;
    private String touserid;
    private String messagetime;
    private String messagecontent;
    private int isview;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTouserid() {
        return touserid;
    }

    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }

    public String getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(String messagetime) {
        this.messagetime = messagetime;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public int getIsview() {
        return isview;
    }

    public void setIsview(int isview) {
        this.isview = isview;
    }
}