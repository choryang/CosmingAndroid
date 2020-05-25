package com.example.cosmingtest;

public class MainData {

    private int iv_profile;
    private String tv_date;
    private String tv_time;
    private String cos_name;
    private String cos_type;
    private String open_date;
    private String due_date;
    private String memo;


    public MainData(int iv_profile, String tv_date, String tv_time, String cos_name, String cos_type, String open_date, String due_date, String memo) {
        this.iv_profile = iv_profile;
        this.tv_date = tv_date;
        this.tv_time = tv_time;
        this.cos_name = cos_name;
        this.cos_type = cos_type;
        this.open_date = open_date;
        this.due_date = due_date;
        this.memo = memo;
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

    public String getCos_name() {
        return cos_name;
    }

    public void setCos_name(String cos_name) {
        this.cos_name = cos_name;
    }

    public String getCos_type() {
        return cos_type;
    }

    public void setCos_type(String cos_type) {
        this.cos_type = cos_type;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
