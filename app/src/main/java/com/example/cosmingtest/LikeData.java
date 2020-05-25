package com.example.cosmingtest;

public class LikeData {

    private String cos_name;
    private String cos_type;
    private String open_date;
    private String due_date;
    private String memo;

    public LikeData(String cos_name, String cos_type, String open_date, String due_date, String memo) {
        this.cos_name = cos_name;
        this.cos_type = cos_type;
        this.open_date = open_date;
        this.due_date = due_date;
        this.memo = memo;
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
