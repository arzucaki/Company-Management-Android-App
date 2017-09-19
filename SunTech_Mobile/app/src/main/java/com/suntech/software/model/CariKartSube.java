package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 18/8/2016.
 */
public class CariKartSube {
    private int Isletme_id;
    private String Isletme_Adi;

    public CariKartSube(JSONObject jo) {
        try {
            setIsletme_id(jo.getInt("Isletme_id"));
            setIsletme_Adi(jo.getString("Isletme_Adi"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIsletme_id() {
        return Isletme_id;
    }

    public void setIsletme_id(int isletme_id) {
        Isletme_id = isletme_id;
    }

    public String getIsletme_Adi() {
        return Isletme_Adi;
    }

    public void setIsletme_Adi(String isletme_Adi) {
        Isletme_Adi = isletme_Adi;
    }
}
