package com.example.cosmingtest;

public class RecordData {

    private int iv_profile;
    private String tv_date;
    private String tv_time;


    public RecordData(int iv_profile, String tv_date, String tv_time) {
        this.iv_profile = iv_profile;
        this.tv_date = tv_date;
        this.tv_time = tv_time;

    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_time() {
        return tv_time;
    }

    public void setTv_time(String tv_time) {
        this.tv_time = tv_time;
    }
}
