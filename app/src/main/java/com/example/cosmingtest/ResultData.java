package com.example.cosmingtest;

public class ResultData {

   // private int ing_id;
    private String ing_name;
    private int ing_ewg;
    private String ing_data;
    private String ing_purpose;

    public ResultData(String ing_name, int ing_ewg, String ing_data, String ing_purpose) {
        //this.ing_id = ing_id;
        this.ing_name = ing_name;
        this.ing_ewg = ing_ewg;
        this.ing_data = ing_data;
        this.ing_purpose = ing_purpose;
    }

    /*public int getIng_id() {
        return ing_id;
    }

    public void setIng_id(int ing_id) {
        this.ing_id = ing_id;
    }*/

    public String getIng_name() {
        return ing_name;
    }

    public void setIng_name(String ing_name) {
        this.ing_name = ing_name;
    }

    public int getIng_ewg() {
        return ing_ewg;
    }

    public void setIng_ewg(int ing_ewg) {
        this.ing_ewg = ing_ewg;
    }

    public String getIng_data() {
        return ing_data;
    }

    public void setIng_data(String ing_data) {
        this.ing_data = ing_data;
    }

    public String getIng_purpose() {
        return ing_purpose;
    }

    public void setIng_purpose(String ing_purpose) {
        this.ing_purpose = ing_purpose;
    }
}
