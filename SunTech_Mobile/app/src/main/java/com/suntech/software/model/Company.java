package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class Company {
    public String getSirket() {
        return Sirket;
    }

    public void setSirket(String sirket) {
        Sirket = sirket;
    }

    public String getIsletme() {
        return Isletme;
    }

    public void setIsletme(String isletme) {
        Isletme = isletme;
    }

    public int getCari_Kart_id() {
        return Cari_Kart_id;
    }

    public void setCari_Kart_id(int cari_Kart_id) {
        Cari_Kart_id = cari_Kart_id;
    }

    public int getIsletme_id() {
        return Isletme_id;
    }

    public void setIsletme_id(int isletme_id) {
        Isletme_id = isletme_id;
    }

    public int getJob_Department_id() {
        return Job_Department_id;
    }

    public void setJob_Department_id(int job_Department_id) {
        Job_Department_id = job_Department_id;
    }

    public String getJob_Department() {
        return Job_Department;
    }

    public void setJob_Department(String job_Department) {
        Job_Department = job_Department;
    }

    public int getRec_Count() {
        return Rec_Count;
    }

    public void setRec_Count(int rec_Count) {
        Rec_Count = rec_Count;
    }

    private String Sirket;
    private String Isletme;
    private int Cari_Kart_id;
    private int Isletme_id;
    private int Job_Department_id;
    private String Job_Department;
    private int Rec_Count;

    public Company(JSONObject jo) {
        try {
            setSirket(jo.getString("Sirket"));
            setIsletme(jo.getString("Isletme"));
            setJob_Department(jo.getString("Job_Department"));
            setCari_Kart_id(jo.getInt("Cari_Kart_id"));
            setIsletme_id(jo.getInt("Isletme_id"));
            setJob_Department_id(jo.getInt("Job_Department_id"));
            setRec_Count(jo.getInt("Rec_Count"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
