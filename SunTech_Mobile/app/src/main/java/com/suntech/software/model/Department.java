package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 18/8/2016.
 */
public class Department {
    private int Kayit_id;
    private String Departman_Adi;


    public Department(JSONObject jo) {
        try {
            setKayit_id(jo.getInt("Kayit_Id"));
            setDepartman_Adi(jo.getString("Departman_Adi"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getKayit_id() {
        return Kayit_id;
    }

    public void setKayit_id(int kayit_id) {
        Kayit_id = kayit_id;
    }

    public String getDepartman_Adi() {
        return Departman_Adi;
    }

    public void setDepartman_Adi(String departman_Adi) {
        Departman_Adi = departman_Adi;
    }
}
